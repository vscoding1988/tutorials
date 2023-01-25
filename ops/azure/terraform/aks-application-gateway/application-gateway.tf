locals {
  backend_address_pool_name      = "${azurerm_virtual_network.this.name}-beap"
  frontend_port_name             = "${azurerm_virtual_network.this.name}-feport"
  frontend_ip_configuration_name = "${azurerm_virtual_network.this.name}-feip"
  http_setting_name              = "${azurerm_virtual_network.this.name}-be-htst"
  listener_name                  = "${azurerm_virtual_network.this.name}-httplstn"
  request_routing_rule_name      = "${azurerm_virtual_network.this.name}-rqrt"
  redirect_configuration_name    = "${azurerm_virtual_network.this.name}-rdrcfg"
}

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
    subnet_id = azurerm_subnet.ag_snet.id
  }

  frontend_port {
    name = local.frontend_port_name
    port = 80
  }

  frontend_ip_configuration {
    name                 = local.frontend_ip_configuration_name
    public_ip_address_id = azurerm_public_ip.ag_ip.id
  }

  // The following block is just here as placeholder, the real data are populated by the ingress
  // inside the AKS cluster
  backend_address_pool {
    name = local.backend_address_pool_name
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
    priority                   = 1
  }

  lifecycle {
    // Very important, the config is populated by the internal ingress controller, so we need to
    // ignore all changes made by the ingress, the config here, is just a placeholder.
    ignore_changes = [
      tags, url_path_map, request_routing_rule, http_listener, probe, backend_http_settings,
      backend_address_pool
    ]
  }
}
