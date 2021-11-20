# Module - Create Log Analytics Workspace and a Dashboard.

[Terraform Documentation](https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs/data-sources/log_analytics_workspace)
## Prerequisites

- existing Resource Group 
## Embed Atom
```terraform
module "log_workspace" {
  source = "./modules/log_workspace"
  env_short = var.env_short
  project_name = var.project_name
}
```
## Atom
```terraform
// Create the Log Analytics Workspace for a given resource group
resource "azurerm_log_analytics_workspace" "law" {
  name                = var.loanw-name
  resource_group_name = data.azurerm_resource_group.rg.name
  location            = data.azurerm_resource_group.rg.location
  sku                 = "PerGB2018"
  retention_in_days   = 30
}

// This references a resource group in Azure, which is not created here
data "azurerm_resource_group" "rg" {
  name = var.rg-name
}
```

## Expected Variables
```terraform
variable "rg-name" {
  description = "Resource group name where to place the Workspace"
  type = string
}

variable "loanw-name" {
  description = "Name of the Workspace"
  type = string
}
```

## Output
Will provide the Log Analytics Workspace id, which is needed for [Application Insights](../application_insights/README.md)
```terraform
output "workspace_id" {
  value     = azurerm_log_analytics_workspace.law.id
  sensitive = true
}
```
