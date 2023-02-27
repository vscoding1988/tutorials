# Implementation of AKS with Azure Application Gateway

Azure Application Gateway is a web traffic load balancer. When combined with kubernetes Application
can be used as an ingress controller. For more detailed in formation on Application Gateway see
the [Microsoft Documentation](https://learn.microsoft.com/en-us/azure/application-gateway/overview).

To use Application Gateway(AG) as an entrypoint to our Kubernetes cluster we have a decision to
make. We can either use the AG just as an entry point and deploy the ingres controller (f.e.
traeffik or ngix) inside the kubernetes cluster, in this case, the AG will just rout all requests to
the internal ingres controller, or we can use the AG as the ingress controller.

The first case is straight forward, just set up the cluster as you normally would do and give the
ingress controller a static cluster IP, and define AG to rout everything to this IP. The second case
is slightly different and this is the one, we want to discuss. Although it looks like Microsoft is
more in favor of the first solution, at least this is what they define in
their [Baseline architecture for an Azure Kubernetes Service (AKS) cluster](../../baseline-aks/readme.md)
.

## Infrastructure

![aks-application-gateway-infrastructure.png](assets/aks-application-gateway-infrastructure.png)

For our setup we will need:

- Application Gateway (AG)
- Azure Kubernetes Service (AKS)
- Vnet around AG & AKS
- two subnets for AG and AKS
- Public IP for the AG

### Namings

The best way to start a new terraform project, is to define alle relevant names. Most of the
companies, will have a naming module which can be either imported, or copied to the project. We
don't have this, so we need create it from scratch.

First we need the relevant variables for name generation and network:

```terraform
variable "environment_config" {
  description = "Configuration for environment"
  type        = object({
    project_name   = string
    env_short      = string
    location_short = string
    location_name  = string
  })
  default = {
    project_name   = "aks-ag-connection"
    env_short      = "dev"
    location_short = "weu"
    location_name  = "West Europe"
  }
}

variable "network" {
  description = "Configure network"
  type        = object({
    spoke_address_space     = string
    spoke_aks_address_space = string
    spoke_aks_lb_address    = string
    spoke_agw_address_space = string
    spoke_lb_address_space  = string
  })
  default = {
    spoke_address_space     = "192.168.0.0/16"
    spoke_aks_address_space = "192.168.1.0/24"
    spoke_aks_lb_address    = "192.168.3.200"
    spoke_agw_address_space = "192.168.2.0/24"
    spoke_lb_address_space  = "192.168.3.0/24"
  }
}
```

For our usecase this is sufficient, but for real project, you will rather split this variable in 4
different and bundle them in the `locals.tf`.

Now we can generate our namings:

```terraform
locals {
  naming = {
    // Resource names
    rg_name       = "rg-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"
    aks_name      = "aks-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"
    aksnodes_name = "rg-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}-aksnodes"
    ag_name       = "ag-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"
    dns_prefix    = "${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"

    // Network names
    vnet_name     = "vnet-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"
    snet_aks_name = "snet-${var.environment_config.env_short}-aks-${var.environment_config.location_short}"
    snet_ag_name  = "snet-${var.environment_config.env_short}-ag-${var.environment_config.location_short}"
    snet_lb_name  = "snet-${var.environment_config.env_short}-lb-${var.environment_config.location_short}"
    ip_name       = "pip-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"
  }
}
```

### Base setup

Now that we have all relevant names, we can create the `main.tf`, here we only have the resource
group and provider config.

```terraform
provider "azurerm" {
  features {}
}

terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.40.0"
    }
  }
}

resource "azurerm_resource_group" "this" {
  location = var.environment_config.location_name
  name     = local.naming.rg_name
}
```

### Network

Let's continue with setting up the network infrastructure in `network.tf`:

```terraform
resource "azurerm_virtual_network" "this" {
  name                = local.naming.vnet_name
  resource_group_name = azurerm_resource_group.this.name
  location            = azurerm_resource_group.this.location
  address_space       = [var.network.spoke_address_space]
}

resource "azurerm_subnet" "aks_snet" {
  name                 = local.naming.snet_aks_name
  resource_group_name  = azurerm_resource_group.this.name
  virtual_network_name = azurerm_virtual_network.this.name
  address_prefixes     = [var.network.spoke_aks_address_space]
}

resource "azurerm_subnet" "agw_snet" {
  name                 = local.naming.snet_agw_name
  resource_group_name  = azurerm_resource_group.this.name
  virtual_network_name = azurerm_virtual_network.this.name
  address_prefixes     = [var.network.spoke_agw_address_space]
}

resource "azurerm_subnet" "lb_snet" {
  name                 = local.naming.snet_lb_name
  resource_group_name  = azurerm_resource_group.this.name
  virtual_network_name = azurerm_virtual_network.this.name
  address_prefixes     = [var.network.spoke_lb_address_space]
}

resource "azurerm_public_ip" "ag_ip" {
  name                = local.naming.ip_name
  resource_group_name = azurerm_resource_group.this.name
  location            = azurerm_resource_group.this.location
  allocation_method   = "Static"
  sku                 = "Standard"
}
```

### Azure Application Gateway

For the application gateway we need to define its frontend (public ip) and a backend (by ingress
created loadbalancer).

```terraform
resource "azurerm_application_gateway" "this" {
  name                = local.naming.ag_name
  resource_group_name = azurerm_resource_group.this.name
  location            = azurerm_resource_group.this.location
  dns_prefix          = local.naming.dns_prefix

  sku {
    name     = "Standard_v2" // Only v2 are supporting function as ingress
    tier     = "Standard_v2"
    capacity = 2
  }

  gateway_ip_configuration {
    name      = "Gateway-IP-Config"
    subnet_id = azurerm_subnet.agw_snet.id
  }

  frontend_port {
    name = local.frontend_port_name
    port = 80
  }

  frontend_ip_configuration {
    name                 = local.frontend_ip_configuration_name
    public_ip_address_id = azurerm_public_ip.ag_ip.id
  }

  // Link to the LoadBalancer created by ingress
  backend_address_pool {
    name         = local.backend_address_pool_name
    ip_addresses = [var.network.spoke_aks_lb_address]
  }

  backend_http_settings {
    name                  = local.http_setting_name
    cookie_based_affinity = "Disabled"
    port                  = 80
    protocol              = "Http"
    request_timeout       = 60
  }

  http_listener {
    name                           = local.listener_name
    frontend_ip_configuration_name = local.frontend_ip_configuration_name
    frontend_port_name             = local.frontend_port_name
    protocol                       = "Http"
  }

  request_routing_rule {
    name                       = local.request_routing_rule_name
    rule_type                  = "Basic"
    http_listener_name         = local.listener_name
    backend_address_pool_name  = local.backend_address_pool_name
    backend_http_settings_name = local.http_setting_name
  }
}
```

### Azure Kubernetes Service

The last piece missing is the AKS, we will place the nodes inside the `aks_snet`.

```terraform
resource "azurerm_kubernetes_cluster" "this" {
  name                             = local.naming.aks_name
  location                         = azurerm_resource_group.this.location
  resource_group_name              = azurerm_resource_group.this.name
  node_resource_group              = local.naming.aksnodes_name
  http_application_routing_enabled = false
  dns_prefix                       = local.naming.dns_prefix

  default_node_pool {
    name           = "default"
    vm_size        = "Standard_B2s"
    node_count     = 1
    // We want to have the node is a specific snet
    vnet_subnet_id = azurerm_subnet.aks_snet.id
  }

  identity {
    type = "SystemAssigned"
  }

  // AKS will use this SP to create a hidden resource group and ingress will use this SP to create a load balancer 
  service_principal {
    client_id     = var.service_principle.client_id
    client_secret = var.service_principle.client_secret
  }
}
```

### Load Balancer config

Finally, lets have a quick look how we can create a load balancer to access it from AGW:

```terraform
resource "kubernetes_service" "azure_lb" {
  metadata {
    name        = "azure-load-balancer"
    annotations = {
      // See https://cloud-provider-azure.sigs.k8s.io/topics/loadbalancer/
      "service.beta.kubernetes.io/azure-load-balancer-internal" : "true"
      "service.beta.kubernetes.io/azure-load-balancer-internal-subnet" : var.loadbalancer_config.subnet
    }
  }

  spec {
    selector = {
      app = "my-app"
    }

    port {
      name        = "http"
      port        = 80
      target_port = 8080
      protocol    = "TCP"
    }

    type = "LoadBalancer"

    // output of var.network.spoke_aks_lb_address ("192.168.3.200")
    load_balancer_ip = var.loadbalancer_config.ip
  }
}
```

This config will create an internal loadbalancer in azure with IP `192.168.3.200` as you remember we
have used this ip for the fronted config of our `azurerm_application_gateway`.

## Further steps

THis was just a brief introduction to how set up an AKS with application gateway. To make it work,
you will need to define a health-probe in `azurerm_application_gateway` and deploy an application
behind the `kubernetes_service.azure_lb` you also should switch to HTTPS.

## Last remarks

This code was extracted from a much larger project and is used for illustration only. The extract
itself was never executed, or tested on the azure system. 


