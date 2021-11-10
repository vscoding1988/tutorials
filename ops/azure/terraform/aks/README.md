# Terraform - AKS
## 
## Prerequisites
- [Terraform Base](../base/terraform-base.md)
## Cluster Module
We will start our configuration by copying the main.tf/variables.tf from the [Terraform Base folder](../base). <br>
For our AKS to work, we will have to create a cluster config first, and then start kubernetes inside, so let's create a Cluster module, 
which will have all our configs needed for the cluster setup. We will start with [variables.tf](./modules/cluster/variables.tf):
### Cluster - variables.tf
```terraform
variable "service_principle_id" {}

variable "service_principle_secret" {}

# the following variables should be configured by the user, so we need to add them to the root variables.tf
variable "location" {
}

variable "kubernetes_version" {
}

variable "rg_name" {
}

variable "cluster_name" {
}

variable "dns_cluster_prefix" {
}
```
Now we will add the new variables to the [root variables.tf](variables.tf) with default values.
```terraform
#[...]
variable "location" {
  default = "westeurope"
}

variable "kubernetes_version" {
  default = "1.22.2"
}

# naming conventions, change when copy
variable "rg_name" {
  default = "rg-aks-vs"
  description = "The name of the created resource group"
}

variable "cluster_name" {
  default = "cluster-aks-vs"
  description = "The name of the created cluster"
}

variable "dns_cluster_prefix" {
  default = "cluster-aks-vs"
  description = "DNS prefix of the created cluster"
}

variable "k8s_namespace" {
  default = "k8s-aks-vs"
  description = "k8s namespace"
}
```
### Cluster - cluster.tf
Now let's create the cluster. We will let terraform create a resource group and the actual cluster.

```terraform
# this will create a resource group in the given environment with the name `rg-vs-aks`
resource "azurerm_resource_group" "rg" {
  location = var.location
  name     = var.rg_name
}

# this will create a resource group in the given environment with the name `rg-vs-aks`
resource "azurerm_kubernetes_cluster" "cluster" {
  name                = var.cluster_name
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  dns_prefix          = var.dns_cluster_prefix
  kubernetes_version  = var.kubernetes_version

  # here we are defining the cluster nodes, how many and which kind of nodes we want to have in our cluster
  default_node_pool {
    name       = "default"
    node_count = 1
    vm_size    = "Standard_E4s_v3"
    type       = "VirtualMachineScaleSets"
    os_disk_size_gb = 30
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
  cluster_name              = var.cluster_name
  dns_cluster_prefix        = var.dns_cluster_prefix
  rg_name                   = var.rg_name

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

# we need to add this variable to the root variables.tf
variable "k8s_namespace" {}
````
We can get all this values from the cluster we have created before. 

### K8S - k8s.tf

```terraform
# create the kubernetes provider with cluster credentials
provider "kubernetes" {
  host                   =  var.host
  client_certificate     =  var.client_certificate
  client_key             =  var.client_key
  cluster_ca_certificate =  var.cluster_ca_certificate
}

# create a namespace for our kubernetes
resource "kubernetes_namespace" "k8s_namespace" {
  metadata {
    name = var.k8s_namespace
  }
}
```

### K8S - Register Module

So lets register the module, by adding:

```terraform
module "k8s" {

  source                = "./modules/k8s/"
  host                  = module.cluster.host
  client_certificate    = base64decode(module.cluster.client_certificate)
  client_key            = base64decode(module.cluster.client_key)
  cluster_ca_certificate= base64decode(module.cluster.cluster_ca_certificate)
  k8s_namespace         = var.k8s_namespace

}
```

Depending on your IDE this part will be marked as an error, that because, cluster currently doesn't expose these variables. 
To make it green, we need to create `output.tf` inside the `cluster` module.

```terraform
output "kube_config" {
  value = azurerm_kubernetes_cluster.cluster.kube_config_raw
}

output "cluster_ca_certificate" {
  value = azurerm_kubernetes_cluster.cluster.kube_config.0.cluster_ca_certificate
}

output "client_certificate" {
  value = azurerm_kubernetes_cluster.cluster.kube_config.0.client_certificate
}

output "client_key" {
  value = azurerm_kubernetes_cluster.cluster.kube_config.0.client_key
}

output "host" {
  value = azurerm_kubernetes_cluster.cluster-vs-aks.kube_config.0.host
}
```

Now we can run ``terraform init`` and after our new module was added the plan 

```shell
terraform plan \
 -var service_principle_id=$SERVICE_PRINCIPAL \
 -var service_principle_secret=$SERVICE_PRINCIPAL_SECRET \
 -var tenant_id=$TENANT_ID \
 -var subscription_id=$SUBSCRIPTION_ID
```
and after it
```shell
terraform apply \
 -var service_principle_id=$SERVICE_PRINCIPAL \
 -var service_principle_secret=$SERVICE_PRINCIPAL_SECRET \
 -var tenant_id=$TENANT_ID \
 -var subscription_id=$SUBSCRIPTION_ID
```
## kubectl
Now we have a running Kubernetes in Azure Cloud we want to access it via kubectl. First we will need the credentials, you can get them from azure.

```shell
# -n = name of the cluster    (var.cluster_name)
# -g = name of resource group (var.rg_name)
az aks get-credentials -n cluster-aks-vs -g rg-aks-vs

# install kubectl
curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
chmod +x ./kubectl
mv ./kubectl /usr/local/bin/kubectl

# check if kubernetes exists
kubectl get svc
# now we can can check if our namespace was created
kubectl get namespaces
```
## Clean Up
Now we can clean up the state by using
```shell
terraform destroy \
 -var service_principle_id=$SERVICE_PRINCIPAL \
 -var service_principle_secret=$SERVICE_PRINCIPAL_SECRET \
 -var tenant_id=$TENANT_ID \
 -var subscription_id=$SUBSCRIPTION_ID
```
