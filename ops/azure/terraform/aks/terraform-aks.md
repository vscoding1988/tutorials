# Terraform - AKS
## Prerequisites
- [Terraform Base](../base/terraform-base.md)
## Cluster Module
We will start our configuration by copying the main.tf/variables.tf from the [Terraform Base folder](../base). <br>
For our AKS to work, we will have to create a cluster config first, and then start kubernetes inside, so let's create a Cluster module, 
which will have all our configs needed for the cluster setup. We will start with [variables.tf](./modules/cluster/variable.tf):
### Cluster - variables.tf
```terraform
variable "service_principle_id" {}

variable "service_principle_secret" {}

# we will need to add this new variables also in our root `variables.tf`
variable "location" {
  default = "westeurope"
}

variable "kubernetes_version" {
  default = "1.22.2"
}
```

We have to propagate the variables we have defined in our [root variables.tf](variables.tf) to the module 
by redefining them in the [modules variables.tf](./modules/cluster/variable.tf). <br> 
Additional we want to specify the kubernetes version and the cluster location. 

### Cluster - cluster.tf
Now let's create the cluster. We will let terraform create a resource group and the actual cluster.

```terraform
# this will create a resource group in the given environment with the name `rg-vs-aks`
resource "azurerm_resource_group" "rg-vs-aks" {
  location = var.location
  name     = "rg-vs-aks"
}

# this will create a cluster, inside our resource group, with the name `cluster-vs-aks`
resource "azurerm_kubernetes_cluster" "cluster-vs-aks" {
  name                = "cluster-vs-aks"
  location            = azurerm_resource_group.rg-vs-aks.location
  resource_group_name = azurerm_resource_group.rg-vs-aks.name
  dns_prefix          = "cluster-vs-aks"
  kubernetes_version  = var.kubernetes_version

  # here we are defining the cluster nodes, how many and which kind of nodes we want to have in our cluster
  default_node_pool {
    name       = "default"
    node_count = 1
    vm_size    = "Standard_E4s_v3"
    type       = "VirtualMachineScaleSets"
    os_disk_size_gb = 30  #lowest possible size
  }

  # we need to specify the service principle
  service_principal {
    client_id     = var.service_principle_id
    client_secret = var.service_principle_secret
  }
}
```

### Cluster - Module registration
After we have created the cluster module, we need to register it and propagate the variables from the root to the modules variables.tf. 
This is done by adding a module block to the [main.tf](main.tf).

```terraform
module "cluster" {

  source                    = "./modules/cluster/"
  service_principle_id      = var.service_principle_id
  service_principle_secret  = var.service_principle_secret
  location                  = var.location
  kubernetes_version        = var.kubernetes_version

}
```

After adding the module we have to re init terraform by executing

```shell
terraform init
```

You should see:
```text
Initializing modules...
- cluster in modules/cluster
```
Now lets see, if our config works by executing the plan command

```shell
terraform plan \
 -var service_principle_id=$SERVICE_PRINCIPAL \
 -var service_principle_secret=$SERVICE_PRINCIPAL_SECRET \
 -var tenant_id=$TENANT_ID \
 -var subscription_id=$SUBSCRIPTION_ID
```
After the execution you should see the changes Terraform will make:

````text
Plan: 2 to add, 0 to change, 0 to destroy.
````

### Cluster - Creation
To finalize the creation we can run the same command as before, but instead of using `plan` we will use `apply` 
```shell
terraform apply \
 -var service_principle_id=$SERVICE_PRINCIPAL \
 -var service_principle_secret=$SERVICE_PRINCIPAL_SECRET \
 -var tenant_id=$TENANT_ID \
 -var subscription_id=$SUBSCRIPTION_ID
```
Again you will see the message from above, and after you will be asked to type 'yes' to apply your changes. <br>
The creation will take some time, after it's finished you can go to the [Azure Portal](https://portal.azure.com/) and check the resource group.
You should see a cluster there. Now when we have checked the creation, lets us destroy the created infrastructure.

```shell
terraform destroy \
 -var service_principle_id=$SERVICE_PRINCIPAL \
 -var service_principle_secret=$SERVICE_PRINCIPAL_SECRET \
 -var tenant_id=$TENANT_ID \
 -var subscription_id=$SUBSCRIPTION_ID
```
## Kubernetes Module
We will start as before by creating a subfolder below modules and name it `k8s`. We create `variables.tf` and `k8s.tf`.

### K8S - variables.tf
We will use [Kubernetes provider](https://registry.terraform.io/providers/hashicorp/kubernetes/latest/docs) for the configuration, 
to make it run, we need to provide it credentials and host. 

````terraform
variable "host" {}

variable "client_certificate" {}

variable "client_key" {}

variable "cluster_ca_certificate" {}
````
We can get all this values from the cluster we have created before. So lets register the module, by adding:

```terraform
module "k8s" {

  source                = "./modules/k8s/"
  host                  = module.cluster.host
  client_certificate    = base64decode(module.cluster.client_certificate)
  client_key            = base64decode(module.cluster.client_key)
  cluster_ca_certificate= base64decode(module.cluster.cluster_ca_certificate)

}
```

Depending on your IDE this part will be marked as an error, that because, cluster currently doesn't expose these variables. 
To make it green, we need to create `output.tf` inside the `cluster` module.

```terraform
output "kube_config" {
  value = azurerm_kubernetes_cluster.cluster-vs-aks.kube_config_raw
}

output "cluster_ca_certificate" {
  value = azurerm_kubernetes_cluster.cluster-vs-aks.kube_config.0.cluster_ca_certificate
}

output "client_certificate" {
  value = azurerm_kubernetes_cluster.cluster-vs-aks.kube_config.0.client_certificate
}

output "client_key" {
  value = azurerm_kubernetes_cluster.cluster-vs-aks.kube_config.0.client_key
}

output "host" {
  value = azurerm_kubernetes_cluster.cluster-vs-aks.kube_config.0.host
}
```
### K8S - k8s.tf


