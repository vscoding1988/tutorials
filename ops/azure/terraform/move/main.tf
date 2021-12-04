provider "azurerm" {
  features {}
}

resource "azurerm_resource_group" "rg" {
  location = "westeurope"
  name     = "rg-vs-mv"
}

module "composed_module" {
  source      = "./modules/composed_module"
  depends_on  = [azurerm_resource_group.rg]

  rg_name     = azurerm_resource_group.rg.name
}

/*#Comment in this block to switch to split mode
module "split_module_1" {
  source      = "./modules/split_module_1"
  depends_on  = [azurerm_resource_group.rg]

  rg_name     = azurerm_resource_group.rg.name
}

module "split_module_2" {
  source      = "./modules/split_module_2"
  depends_on  = [azurerm_resource_group.rg]

  rg_name     = azurerm_resource_group.rg.name
}
*/
