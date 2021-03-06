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
