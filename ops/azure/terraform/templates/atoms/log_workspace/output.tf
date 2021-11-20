// The id can be used by Application Insights to feed it data to the Workspace. Application Insights without a Workspace are deprecated
output "workspace_id" {
  value     = azurerm_log_analytics_workspace.law.id
  sensitive = true
}
