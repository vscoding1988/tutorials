variable "asp_name" {
  description = "Name of the app service plan"
  type = string
}

variable "rg_name" {
  description = "Resource group name where to place the Dashboard/Workspace"
  type = string
}

variable "asp_tier" {
  description = "Define the tier of the service plan"
  type = string
  default = "PremiumV2"
}
variable "asp_size" {
  description = "Define the size of the service plan"
  type = string
  default = "P1v2"
}