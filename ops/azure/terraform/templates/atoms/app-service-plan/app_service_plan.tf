// Create App Service plan in a given resource group
resource "azurerm_app_service_plan" "asp" {
  name                = var.asp_name
  location            = data.azurerm_resource_group.rg.location
  resource_group_name = data.azurerm_resource_group.rg.name
  kind                = "Linux"
  reserved            = true

  sku {
    tier = var.asp_tier
    size = var.asp_size
  }
}

// This references a resource group in Azure, which is not created here
data "azurerm_resource_group" "rg" {
  name = var.rg_name
}