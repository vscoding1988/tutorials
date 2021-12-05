# Creation of a Resource Group

This module will create a `Resource Group` in Azure for provided name.
## Resources

| Name | Type |
|------|------|
| [azurerm_resource_group.rg](https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs/resources/resource_group) | resource |
## Inputs

| Name | Description | Type | Default | Required |
|------|-------------|------|---------|:--------:|
| <a name="input_location"></a> [location](#input\_location) | Location of the created resource. | `string` | `"westeurope"` | no |
| <a name="input_name"></a> [name](#input\_name) | Name of the created resource. | `string` | `"rg-vs-docs"` | no |
## Examples
```terraform
// Minimal specification
module "create_rg" {
  source = "../../terraform-docs"
}
```
```terraform
// Maximal specification
module "create_rg" {
  source    = "../../terraform-docs"

  name      = "rg-my-name"
  location  = "eastasia"
}
```
## Outputs

| Name | Description |
|------|-------------|
| <a name="output_location"></a> [location](#output\_location) | Resource group location. |
| <a name="output_name"></a> [name](#output\_name) | Resource group name. |
## Providers

| Name | Version |
|------|---------|
| <a name="provider_azurerm"></a> [azurerm](#provider\_azurerm) | ~> 2.88.0 |
## Requirements

| Name | Version |
|------|---------|
| <a name="requirement_terraform"></a> [terraform](#requirement\_terraform) | ~> 1.0.10 |
| <a name="requirement_azurerm"></a> [azurerm](#requirement\_azurerm) | ~> 2.88.0 |
## Modules

No modules.

