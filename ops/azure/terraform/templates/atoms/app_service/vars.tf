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
    url         = string
    username    = string
    password    = string
    image_path  = string
  })
}

# Optional
variable "health_check_path" {
  description = "Url to the health endpoint"
  type = string
  default = "/actuator/health"
}

variable "allowed_origins" {
  description = "Domains for the CORS config"
  type = list(string)
  default = []
}

variable "additional_app_settings" {
  type = map(string)
  sensitive = true
  default = null
}

