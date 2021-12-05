# Terraform - Docs
- [terraform-docs project on GitHub](https://github.com/terraform-docs/terraform-docs)
- [terraform-docs documentation](https://terraform-docs.io/user-guide/introduction/)
## Install
There is a couple of ways to install `terraform-docs`, you will find them on GitHub, the one I'm using for my [custom azure cli docker image](../../custom-azure-cli-image/README.md) is the following:
```shell
curl -Lo ./terraform-docs.tar.gz https://github.com/terraform-docs/terraform-docs/releases/download/v0.16.0/terraform-docs-v0.16.0-$(uname)-amd64.tar.gz
tar -xzf terraform-docs.tar.gz
chmod +x terraform-docs
mv terraform-docs /usr/local/bin/terraform-docs
```
## First doc 
To document a project, we first need a project, so let's create a simple `main.tf` which create a `ressource group` in `azure` and expects a certain terraform/provider version.

```terraform
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
  location = "westeurope"
  name     = "rg-vs-docs"
}
```
Now we can create our first doc.
```shell
terraform-docs markdown table . > first_doc.md
```
The [result](first_doc.md) is kinda disappointing. Let's spice up the doc a little.

## Second doc
First lets make the `resource group` name and location configurable by creating a `vars.tf`.
```terraform
variable "name" {
  description = "Name of the created resource"
  type        = string
  default     = "rg-vs-docs"
}

variable "location" {
  description = "Location of the created resource"
  type        = string
  default     = "westeurope"
}
```
Now we should use the variables in our `main.tf`
```terraform
resource "azurerm_resource_group" "rg" {
  location = var.location
  name     = var.name
}
```
Second we add the following snippet to the beginning of our `main.tf`
```terraform
/**
* # Creation of a Resource Group
*
* This module will create a `Resource Group` in Azure for provided name.
* 
*/
```
The last thing we want to add would be an `outputs.tf` for name and location.
```terraform
output "name" {
  value       = azurerm_resource_group.rg.name
  description = "Resource group name."
}

output "location" {
  value       = azurerm_resource_group.rg.location
  description = "Resource group location."
}
```
It's time for our second doc.
```shell
terraform-docs markdown table . > second_doc.md
```
The [result](second_doc.md) is much better and depending on your use case this may be enough, but there is one thing I want to explore with you.

## Providing examples
In this step is our goal to rearrange the created documentation and add some examples for our module.
This is may be a little deep, but hang in there, we will have a little peek into the template configuration. Here are the relevant docs:
- [Configuration documentation](https://terraform-docs.io/user-guide/configuration/)
- [Content Documentation](https://terraform-docs.io/user-guide/configuration/content/)
- [Include Examples](https://terraform-docs.io/how-to/include-examples/)

First we will create a new folder examples and place there two files.

```terraform
# min.tf
module "create_rg" {
  source = "../../terraform-docs"
}
```
```terraform
# max.tf
module "create_rg" {
  source    = "../../terraform-docs"

  name      = "rg-my-name"
  location  = "eastasia"
}
```
To embed the examples we will need to alter the template. Let's create the `.terraform-docs.yml`. 
```yaml
formatter: "markdown table"
content: |-
  {{ .Header }}
  {{ .Resources }}
  {{ .Inputs }}
  ## Examples
  ``terraform
  // Minimal specification
  {{ include "examples/min.tf" }}
  ``
  ``terraform
  // Maximal specification
  {{ include "examples/max.tf" }}
  ``
  {{ .Outputs }}
  {{ .Providers }}
  {{ .Requirements }}
  {{ .Modules }}
  {{ .Footer }}
```
The configuration will be picked up automatically and because the formatter is now defined in the config we just need to execute
```shell
terraform-docs . > final_doc.md
```
## Conclusion
Today we learned, how to use `terrafrom_docs` to document our terraform modules automatically. We had a brief look at the config and were able to change the default template.
