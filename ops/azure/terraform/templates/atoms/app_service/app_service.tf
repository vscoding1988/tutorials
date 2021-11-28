// Create Application Service in a given resource group based on the given Application Service Plan
// The Application Service will pull a image from a given docker_path

resource "azurerm_app_service" "as" {
  name                        = var.as_name
  location                    = data.azurerm_resource_group.rg.location
  resource_group_name         = data.azurerm_resource_group.rg.name
  app_service_plan_id         = var.app_service_plan_id
  https_only                  = true

  site_config {
    linux_fx_version          = "DOCKER|${var.docker_config.image_path}"
    ftps_state                = "FtpsOnly"
    always_on                 = true

    cors {
      allowed_origins         = var.allowed_origins
    }

    health_check_path         = var.health_check_path
  }

  app_settings = merge(var.additional_app_settings, {
    "DOCKER_ENABLE_CI"                    = true
    "DOCKER_REGISTRY_SERVER_URL"          = var.docker_config.url
    "DOCKER_REGISTRY_SERVER_USERNAME"     = var.docker_config.username
    "DOCKER_REGISTRY_SERVER_PASSWORD"     = var.docker_config.password

    "WEBSITES_PORT"                       = "8080"
    "WEBSITES_CONTAINER_START_TIME_LIMIT" = "1800"
    "WEBSITE_WARMUP_PATH"                 = var.health_check_path
    // Healthcheck: check 5 times before removing instance from loadbalancer
    "WEBSITE_HEALTHCHECK_MAXPINGFAILURES" = "5"
  })

  identity {
    type = "SystemAssigned"
  }
}

// This references a resource group in Azure, which is not created here
data "azurerm_resource_group" "rg" {
  name = var.rg_name
}
