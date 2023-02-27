// Network Configuration
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
