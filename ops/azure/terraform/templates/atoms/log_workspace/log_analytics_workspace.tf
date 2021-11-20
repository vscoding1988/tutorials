// Create the Log Analytics Workspace for a given resource group
resource "azurerm_log_analytics_workspace" "law" {
  name                = var.loanw-name
  resource_group_name = data.azurerm_resource_group.rg.name
  location            = data.azurerm_resource_group.rg.location
  sku                 = "PerGB2018"
  retention_in_days   = 30
}

// This references a resource group in Azure, which is not created here
data "azurerm_resource_group" "rg" {
  name = var.rg-name
}