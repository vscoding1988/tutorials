variable "environment_config" {
  description = "Configuration for environment"
  type        = object({
    project_name   = string
    env_short      = string
    location_short = string
    location_name  = string
  })
  default = {
    project_name   = "aks-ag-connection"
    env_short      = "dev"
    location_short = "weu"
    location_name  = "West Europe"
  }
}
