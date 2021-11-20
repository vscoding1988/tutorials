# Atom - Application Service

[Terraform Documentation](https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs/resources/app_service)
## Prerequisites

- existing Resource Group
- existing [Application Service Plan](../app-service-plan/README.md)

## Embed Atom
```terraform
module "app_service" {
  source                      = "./modules/app_service"
  depends_on                  = [app_service_plan] //Optional, if you provide the ID just leave it out

  as_name                     = var.as_name
  rg_name                     = var.rg_name
  aps_service_plan_id         = app_service_plan.id       //You can also provide a plain ID
  docker_config               = var.docker_config
  
  //Optional
  //health_check_path           = var.health_check_path
  //allowed_origins             = var.allowed_origins
  //additional_app_settings     = var.additional_app_settings 
}
```
## Atom
```terraform
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

    "JAVA_OPTS" = "-Dserver.port=80"
    "WEBSITES_CONTAINER_START_TIME_LIMIT" = "1800"

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
```
## Expected Variables
```terraform
variable "as_name" {
  description = "Name of the app service"
  type = string
}

variable "rg_name" {
  description = "Resource group name where to place the Dashboard/Workspace"
  type = string
}

variable "app_service_plan_id" {
  description = "App Service Plan ID"
  type = string
}

// Docker variables

variable "docker_config" {
  description   = "Docker configuration object, for pulling the image. If using open image from github, you just need to provide the image_path"
  type = object({
    url         = string,
    username    = string,
    password    = string,
    image_path  = string
  }),
  default = object({
    url         = "https://hub.docker.com/",
    username    = null,
    password    = null,
    image_path  = null
  })
}

# Optional
variable "health_check_path" {
  description = "Url to the health endpoint"
  type = string
  default = "/actuator/health"
}

variable "allowed_origins" {
  description = "Environment short [dev|qa|prod]"
  type = list(string)
  default = []
}

variable "additional_app_settings" {
  type = map(string)
  sensitive = true
  default = null
}
```

