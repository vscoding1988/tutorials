# Terraform Import

Terraform [import](https://www.terraform.io/cli/commands/import) command can be used to import created 
resources in your state. To import a resource you will need its id. Each cloud provider has its own 
system to generate id's and how to access it. In Azure you can go to the resource -> Settings -> Properties.

If the resource is created by terraform, and you want to import it to another state (state merge) you can
also download the `.tfstate` file and search there for the id.

```shell
terraform import -var-file=dev.tfvars module.shared.azurerm_monitor_diagnostic_setting.frontend-st-diagnostic "$id"
```
