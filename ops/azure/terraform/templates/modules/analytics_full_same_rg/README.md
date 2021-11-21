# Composition - Create Log Analytics Workspace and Application Insights

This Module will create "Log Analytics Workspace" and "Application Insights" in the same Resource Group, our Application is placed. 
It provides `instrumentation_key` which can be provided to the Application Service
## Prerequisites

- existing Resource Group
## Used Atoms

- [Log Workspace](../../atoms/log_workspace/README.md)
- [Application Insights](../../atoms/application_insights/README.md)

## Embed Module

```terraform
// Will create a "Log Analytics Workspace" with corresponding App Insights
module "analytics" {
  source                      = "./modules/analytics_full_same_rg"
  naming                      = local.naming
}
```
## Output
```terraform
// This key is used by Application Service for pushing it´s  date to the Application Insights
output "instrumentation_key" {
  value = module.application_insights.instrumentation_key
}
```
