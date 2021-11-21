# Module - Create Log Analytics Workspace and a Dashboard.

## Prerequisites

- existing resource group 
- existing [Log Analytics Workspace](../log_workspace/README.md) 
## Embed Atom
```terraform
module "application_insights" {
  source = "./modules/application-insights"
  env_short = var.env_short
  project_name = var.project_name
  workspace_id = var.workspace_id //if you want to log in a specific already existing workspace, you can use data to represent it
  //retention_days = 90 //optional
}
```

## Atoms
```terraform
// Create Insights in a given resource group
resource "azurerm_application_insights" "appi" {
  name                = local.appi-name
  location            = data.azurerm_resource_group.rg.location
  resource_group_name = data.azurerm_resource_group.rg.name
  application_type    = "other"
  retention_in_days   = var.retention_days
  disable_ip_masking  = true
  workspace_id        = var.workspace_id

  lifecycle {
    prevent_destroy = true
  }
}

// This references a resource group in Azure, which is not created here
data "azurerm_resource_group" "rg" {
  name = local.rg-name
}
```
## Expected Variables
```terraform
variable "rg-name" {
  description = "Resource group name where to place the Dashboard/Workspace"
  type = string
}

variable "appi-name" {
  description = "Resource group name where to place the Dashboard/Workspace"
  type = string
}

variable "workspace_id" {
  description = "Id of the workspace the insights is feeding its data to, it can be optional, but this way of creating insights without workspace is deprecated"
  type = string
  default = ""
}

variable "application_type" {
  description = "Type of our application [ios|java|MobileCenter|Node.JS|other|phone|store|web]"
  type = string
  default = "java"
}

variable "retention_days" {
  description = "Retention days for the insights"
  type = number
  default = 90
}
```
## Output
```terraform
// This key is used by Application Service for pushing it´s  date to the Application Insights
output "instrumentation_key" {
  value = azurerm_application_insights.appi.instrumentation_key
}
```
