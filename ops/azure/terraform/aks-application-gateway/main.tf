provider "azurerm" {
  features {}
}

terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.40.0"
    }
  }
}

resource "azurerm_resource_group" "this" {
  location = var.environment_config.location_name
  name     = local.naming.rg_name
}
