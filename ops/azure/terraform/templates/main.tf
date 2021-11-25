# install Terraform Azure plugin and provide it with the connection variables. The variables are defined in `variables.tf`
provider "azurerm" {
  features {}
}
resource "azurerm_resource_group" "rg" {
  location          = var.location
  name              = local.naming.rg_name
}

module "analytics" {
  source            = "./modules/analytics_full_same_rg"
  depends_on        =  [azurerm_resource_group.rg]

  naming            = local.naming
}

module "app_service" {
  source            = "./modules/app_service_with_plan"
  depends_on        =  [module.analytics]

  app_insights_key  = module.analytics.instrumentation_key
  naming            = local.naming
  docker_config     = local.docker_config
}
