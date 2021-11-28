# Enable Application insights for Application Service
All modules used here can be found in the templates' folder. Basically we just create an Application Service with a [chatty spring boot app](../../../../spring-boot/chatty-spring-app/README.md).
The app will generate every couple of seconds random logs and errors. Our goal is to feed our Log Analytics Workspace with data through Application insights.

## Basic Information
If you have a Java Application and you want to feed your data to application insights you can attach an Azure java agent 
to your app and provide it with Instrumentation key from your Application Insights (full doc can be found [here](https://docs.microsoft.com/en-us/azure/azure-monitor/app/java-in-process-agent)).
When you have your app deployed to an Azure Application Service azure will do it for you, if you go to you `App Service -> Settings -> Application Insights` 
it will set some Environment properties to your app and in few clicks your app is connected to Application Insights. But when we want to do it through Terraform we will need to set
properties by our self.

## Terraform Config
The following environment variables should be provided to your Application Service.
```terraform
app_settings = {
  # Application insights instrumentation key
  "APPINSIGHTS_INSTRUMENTATIONKEY"                  = var.app_insights_key
  # Connection string replaces INSTRUMENTATION_KEY https://docs.microsoft.com/en-us/azure/azure-monitor/app/sdk-connection-string
  "APPLICATIONINSIGHTS_CONNECTION_STRING"           = "InstrumentationKey=${var.app_insights_key}"
  
  # Profiler version https://docs.microsoft.com/en-us/azure/azure-monitor/app/profiler#enable-profiler-manually-or-with-azure-resource-manager
  "APPINSIGHTS_PROFILERFEATURE_VERSION"             = "1.0.0"
  "DiagnosticServices_EXTENSION_VERSION"            = "~3"
  
  # Snapshot debuger version https://docs.microsoft.com/en-us/azure/azure-monitor/app/snapshot-debugger (currently supports only .NET apps)
  "APPINSIGHTS_SNAPSHOTFEATURE_VERSION"             = "1.0.0"
  "SnapshotDebugger_EXTENSION_VERSION"              = "disabled"

  # Configuration content can be used to configure the insights embeding https://docs.microsoft.com/en-us/azure/azure-monitor/app/java-standalone-config
  # It is useful to at least provide the application name
  "APPLICATIONINSIGHTS_CONFIGURATION_CONTENT"       = "{\"role\": {\"name\": \"${var.naming.as_name}\"}}"
  
  # Version of the java agent, which will be attached, to sure to keep this version up to date
  "ApplicationInsightsAgent_EXTENSION_VERSION"      = "~3"
  
  # https://docs.microsoft.com/en-us/azure/azure-monitor/app/azure-web-apps-net#application-settings-definitions
  "InstrumentationEngine_EXTENSION_VERSION"         = "disabled"
  # It seams like this will profile SQL queries https://docs.microsoft.com/en-us/azure/azure-monitor/app/azure-web-apps-net#application-settings-definitions
  "XDT_MicrosoftApplicationInsights_BaseExtensions" = "disabled"
  "XDT_MicrosoftApplicationInsights_Mode"           = "recommended"
  # Enables Interop (interoperation) with Application Insights SDK. For ASP.NET Core apps only. https://docs.microsoft.com/en-us/azure/azure-monitor/app/azure-web-apps-net-core?tabs=Windows%2Cwindows#application-settings-definitions
  "XDT_MicrosoftApplicationInsights_PreemptSdk"     = "disabled"
}
```


