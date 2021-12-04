# Terraform State - Move
We have one module with two storage accounts, after creating the accounts, we want to split the module in two, with one account in each module.

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
```shell
#Because we have changed the module structure, we need to execute init, before we can run plan
terraform init
terraform plan
```
Plan you will show us `Plan: 2 to add, 0 to change, 2 to destroy.`
## Move
So that terraform can recognize our storage accounts as the same in split modules we need to move them in state file. 
For that we have the terraform command [mv](https://www.terraform.io/docs/cli/commands/state/mv.html). It can be used 
for renaming or moving of resources. We will need to define the full path of the resource, we want to move. 
The full path is build by: `<modulePrefix>.<moduleName>.<resourceType>.<resourceName>`. If your resource is not inside a module, 
you can just use `<resourceType>.<resourceName>` as path. By following the rule we get `module.composed_module.azurerm_storage_account.st_1` 
as source path for first storage account and `module.split_module_1.azurerm_storage_account.st_1` as target path.

```shell
# Lets move both accounts to their new module, after each execution you should get `Successfully moved 1 object(s).`
terraform state mv module.composed_module.azurerm_storage_account.st_1 module.split_module_1.azurerm_storage_account.st_1
terraform state mv module.composed_module.azurerm_storage_account.st_2 module.split_module_2.azurerm_storage_account.st_2
# When we run plan again we should not get any changes
terraform plan
```
## Renaming
To make the list complete let's rename one of the storage accounts and use move to update state. 
Got to [main.tf](modules/split_module_1/main.tf) and set resource name from `st_1` to `st_1_new`.
```terraform
resource "azurerm_storage_account" "st_1_new" {
  name                     = "stvsaccount1"
  resource_group_name      = data.azurerm_resource_group.rg.name
  location                 = data.azurerm_resource_group.rg.location
  account_tier             = "Standard"
  account_replication_type = "GRS"
}
```
```shell
terraform plan
# as expected terraform plan shows `Plan: 1 to add, 0 to change, 1 to destroy.` We need to rename the module
terraform state mv module.split_module_1.azurerm_storage_account.st_1 module.split_module_1.azurerm_storage_account.st_1_new
terraform plan
# and once again we get "No changes"
```
# Conclusion
Today we learned how to split modules without deleting them and how we can change the name of a resource. 
