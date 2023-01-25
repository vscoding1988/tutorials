resource "azurerm_kubernetes_cluster" "this" {
  name                             = local.naming.aks_name
  location                         = azurerm_resource_group.this.location
  resource_group_name              = azurerm_resource_group.this.name
  node_resource_group              = local.naming.aksnodes_name
  http_application_routing_enabled = false

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

  network_profile {
    network_plugin     = "azure"
    dns_service_ip     = "10.0.0.10"
    docker_bridge_cidr = "172.17.0.1/16"
    service_cidr       = "10.0.0.0/16"
  }

  // Register Applicatione Gateway as a ingress controller
  ingress_application_gateway {
    gateway_id = azurerm_application_gateway.this.id
  }
}
