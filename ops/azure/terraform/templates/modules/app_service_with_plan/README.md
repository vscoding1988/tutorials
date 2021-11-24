# Composition - Create App Service with a new App Service Plan  

This Composition will create "Application Service" and "Application Service Plan" in the given resource group. 

## Prerequisites

- existing resource group 
- optional: [Application Insights ID](../../atoms/application_insights/README.md)
## Used Modules

- [Application Service](../../atoms/app_service/README.md)
- [Application Service Plan](../../atoms/app_service_plan/README.md)

## Embed Module

```terraform
// Will create a "Application Service" with a new Application Service Plan
module "app_service_with_plan" {
  source                      = "./modules/app_service_with_plan"
  naming                      = local.naming
  docker_config               = local.docker_config
  //app_insights_key            = module.analytics.instrumentation_key optional
}
```

## Expected Variables
```terraform
variable "naming" {
  type = object({
    rg_name = string
    as_name = string
    asp_name = string
  })
}

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


variable "app_insights_key" {
  description = "Provide App Insights key to enable monitoring, if not provided, the application will not be monitored"
  type = string
  default = null
}
```
## Output
```terraform
output "instrumentation_key" {
  value = azurerm_application_insights.appi.instrumentation_key
}
```
