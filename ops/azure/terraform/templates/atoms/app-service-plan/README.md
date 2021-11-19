# Atom - Application Service Plan

[Terraform Documentation](https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs/data-sources/app_service_plan)
## Prerequisites

- existing resource group where the App-Service Plan will be placed

## Embed Module
```terraform
module "app_service_plan" {
  source    = "./modules/app-service-plan"
  asp_name  = local.asp_name
  rg_name   = local.rg_name
  // Default values
  //asp_tier = "PremiumV2"
  //asp_size = "P1v2"
}
```
## Atom
```terraform
// Create App Service plan in a given resource group
resource "azurerm_app_service_plan" "asp" {
  name                = local.asp-name
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
  name = local.rg-name
}
```

## Expected Variables
```terraform
# required
variable "asp_name" {
  description = "Name of the App Service Plan (ASP)"
  type = string
}

variable "rg_name" {
  description = "Resource group name where to place the App Service Plan (ASP)"
  type = string
}

# optional
variable "asp_tier" {
  description = "Define the tier of the App Service Plan (ASP)"
  type = string
  default = "PremiumV2"
}

variable "asp_size" {
  description = "Define the size of the App Service Plan (ASP)"
  type = string
  default = "P1v2"
}
```

## Output
Will provide the Application Service Plan id, which is needed for [Application Service](../app_service/README.md)
```terraform
output "id" {
  value       = azurerm_app_service_plan.asp.id
  sensitive   = true
  description = "Id of the App Service Plan"
}
```
