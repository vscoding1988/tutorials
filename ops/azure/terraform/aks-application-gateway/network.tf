// Network Configuration
resource "azurerm_virtual_network" "this" {
  name                = local.naming.vnet_name
  resource_group_name = azurerm_resource_group.this.name
  location            = azurerm_resource_group.this.location
  address_space       = ["192.168.0.0/16"]
}

resource "azurerm_subnet" "aks_snet" {
  name                 = local.naming.snet_aks_name
  resource_group_name  = azurerm_resource_group.this.name
  virtual_network_name = azurerm_virtual_network.this.name
  address_prefixes     = ["192.168.1.0/24"]
}

resource "azurerm_subnet" "ag_snet" {
  name                 = local.naming.snet_ag_name
  resource_group_name  = azurerm_resource_group.this.name
  virtual_network_name = azurerm_virtual_network.this.name
  address_prefixes     = ["192.168.2.0/24"]
}

resource "azurerm_public_ip" "ag_ip" {
  name                = local.naming.ip_name
  resource_group_name = azurerm_resource_group.this.name
  location            = azurerm_resource_group.this.location
  allocation_method   = "Static"
  sku                 = "Standard"
}
