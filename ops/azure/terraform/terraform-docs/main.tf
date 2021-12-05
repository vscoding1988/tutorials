/**
* # Creation of a Resource Group
*
* This module will create a `Resource Group` in Azure for provided name.
*
*/

terraform {
  required_version = "~> 1.0.10"
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 2.88.0"
    }
  }
}

resource "azurerm_resource_group" "rg" {
  location = var.location
  name     = var.name
}
