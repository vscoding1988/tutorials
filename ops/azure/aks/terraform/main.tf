# install Terraform Azure plugin and provide it with the connection variables. The variables are defined in `variables.tf`
provider "azurerm" {
  version = "=2.84.0"

  subscription_id = var.subscription_id
  client_id       = var.service_principle_id
  client_secret   = var.service_principle_key
  tenant_id       = var.tenant_id

  features {}
}
