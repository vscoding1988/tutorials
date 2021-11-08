# this will create a resource group in the given environment with the name `rg-vs-aks`
resource "azurerm_resource_group" "rg-vs-aks" {
  location = var.location
  name     = "rg-vs-aks"
}

# this will create a resource group in the given environment with the name `rg-vs-aks`
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
    os_disk_size_gb = 30
  }

  # we need to specify the service principle
  service_principal {
    client_id     = var.service_principle_id
    client_secret = var.service_principle_secret
  }
}
