locals {
  naming = {
    // Resource names
    rg_name       = "rg-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"
    aks_name      = "aks-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"
    aksnodes_name = "rg-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}-aksnodes"
    ag_name       = "ag-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"

    // Network names
    vnet_name     = "vnet-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"
    snet_aks_name = "snet-${var.environment_config.env_short}-aks-${var.environment_config.location_short}"
    snet_ag_name  = "snet-${var.environment_config.env_short}-ag-${var.environment_config.location_short}"
    ip_name       = "pip-${var.environment_config.env_short}-${var.environment_config.project_name}-${var.environment_config.location_short}"
  }
}
