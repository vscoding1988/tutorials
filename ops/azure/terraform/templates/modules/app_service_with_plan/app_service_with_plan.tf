// Will create a "App Service Plan"
module "app_service_plan" {
  source                      = "../../atoms/app_service_plan"
  asp_name                    = var.naming.asp_name
  rg_name                     = var.naming.rg_name
}

// Will create a "App Service"
module "app_service" {
  source                      = "../../atoms/app_service"
  depends_on                  = [module.app_service_plan]

  app_service_plan_id         = module.app_service_plan.id

  as_name                     = var.naming.as_name
  rg_name                     = var.naming.asp_name
  docker_config               = var.docker_config

  // provide Application Insights settings, if key is given
  additional_app_settings     = var.app_insights_key != null ? local.app_insights_settings : {}
}
