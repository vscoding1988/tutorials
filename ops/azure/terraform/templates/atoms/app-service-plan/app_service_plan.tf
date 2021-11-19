// Create Application Service Plan in a given resource group
resource "azurerm_app_service_plan" "asp" {
  name                = var.asp_name
  location            = data.azurerm_resource_group.rg.location
  resource_group_name = data.azurerm_resource_group.rg.name
  kind                = "Linux"

  //https://stackoverflow.com/questions/59901840/what-is-the-reserved-argument-in-azure-app-service-plan-terraform-config
  //If you want to create Linux instance make sure to have set this to true
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
