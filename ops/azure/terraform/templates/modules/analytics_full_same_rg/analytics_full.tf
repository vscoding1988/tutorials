// Will create a "Log Analytics Workspace"
module "log_workspace" {
  source                      = "../../atoms/log_workspace"

  loanw-name                  = var.naming.loanw_name
  rg-name                     = var.naming.rg_name
}

// Will create a "App Insights" and link it to the workspace
module "application_insights" {
  source                      = "../../atoms/application_insights"
  depends_on                  = [module.log_workspace]

  workspace_id                = module.log_workspace.workspace_id
  rg-name                     = var.naming.rg_name
  appi-name                   = var.naming.appi_name
}
