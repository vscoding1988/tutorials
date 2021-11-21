// This key is used by Application Service for pushing it´s  date to the Application Insights
output "instrumentation_key" {
  value = azurerm_application_insights.appi.instrumentation_key
}
