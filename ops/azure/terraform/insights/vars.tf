variable "url" {
  description = "Domain of your docker repository (f.e. https://hub.docker.com/)"
  type        = string
}

variable "username" {
  description = "Username for the repository"
  type        = string
  sensitive   = true
  default     = null
}

variable "password" {
  description = "Password for the repository"
  type        = string
  sensitive   = true
  default     = null
}

variable "image_path" {
  description = "Password for the repository"
  type        = string
}

variable "project_name" {
  description = "Project name, will be included in the name of all created resources"
  type        = string
}

variable "environment" {
  description = "The current environment [DEV|QA|PROD]"
  type        = string
  default     = "dev"
}

variable "location" {
  description = "The location we want to place our resource"
  type        = string
  default     = "westeurope"
}
