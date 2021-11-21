// Create Insights in a given resource group
resource "azurerm_application_insights" "appi" {
  name                = var.appi-name
  location            = data.azurerm_resource_group.rg.location
  resource_group_name = data.azurerm_resource_group.rg.name
  application_type    = var.application_type
  retention_in_days   = var.retention_days
  disable_ip_masking  = true
  workspace_id        = var.workspace_id
}

// This references a resource group in Azure, which is not created here
data "azurerm_resource_group" "rg" {
  name = var.rg-name
}
