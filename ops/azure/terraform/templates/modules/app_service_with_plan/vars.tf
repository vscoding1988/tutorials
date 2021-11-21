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
