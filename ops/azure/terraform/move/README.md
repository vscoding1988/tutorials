# Terraform - Move
We have one module with two storage accounts, after creating the account, we want to split the module in two.

## Create resources
First we want to create the two storage accounts from `composed_module`. Make sure the [main.tf](main.tf) includes only the `composed_module`
```terraform
module "composed_module" {
  source      = "./modules/composed_module"
  depends_on  = [azurerm_resource_group.rg]

  rg_name     = azurerm_resource_group.rg.name
}
```
```shell
# Execute plan
terraform plan
# you will see 3 creations (1 resource group + 2 storage accounts)

# now execute apply
terraform apply
```
## Split module
Now we want to split the module, remove the `composed_module` include from [main.tf](main.tf) and comment in, the `split_module`
```terraform
module "split_module_1" {
  source      = "./modules/split_module_1"
  depends_on  = [azurerm_resource_group.rg]

  rg_name     = azurerm_resource_group.rg.name
}

module "split_module_2" {
  source      = "./modules/split_module_2"
  depends_on  = [azurerm_resource_group.rg]

  rg_name     = azurerm_resource_group.rg.name
}
```
