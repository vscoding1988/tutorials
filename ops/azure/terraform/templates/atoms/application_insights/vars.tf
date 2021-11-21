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
