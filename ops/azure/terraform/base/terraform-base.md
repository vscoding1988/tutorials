# Terraform - Azure

Azure Provider Doc: https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs
## Prerequisites
- [Running Azure CLI](../../cli/getting-started.md)
- [Existing service principal (with contributor role)](../../cli/create-service-principal.md) (optional)

## Install Terraform
For the installation we will use the docker container we have already used in the Azure CLI tutorial. Of course, you can install everything locally. 

```shell
# download terraform (to get a newer version go to https://www.terraform.io/downloads.html)
curl -o /tmp/terraform.zip -LO https://releases.hashicorp.com/terraform/1.0.10/terraform_1.0.10_linux_amd64.zip

# unzip the downloaded file
unzip /tmp/terraform.zip

# give execution rights and move to user folder
chmod +x terraform && mv terraform /usr/local/bin/
```

## Terraform - Files

Now we are ready to create the Terraform config.

Let's start by creating [variables.tf](./variables.tf). We will define the credentials for Azure there.

```terraform
variable "service_principle_id" {}

variable "service_principle_secret" {}

variable "tenant_id" {}

variable "subscription_id" {}
```

Now we can create [main.tf](./main.tf) and install the Azure plugin

```terraform
provider "azurerm" {
  
  subscription_id = var.subscription_id
  client_id       = var.service_principle_id
  client_secret   = var.service_principle_secret
  tenant_id       = var.tenant_id
  
  features {}
}
```
Our basic setup is done, so we can initialize Terraform in the command line.

```shell
terraform init
```
This command will download the plugin in the folder `.terraform`

Now we can execute the terraform plan. We have no instructions created yet, so it is just a test for the principal.

```shell
terraform plan \
-var service_principle_id=$SERVICE_PRINCIPAL \
-var service_principle_secret=$SERVICE_PRINCIPAL_SECRET \
-var tenant_id=$TENANT_ID \
-var subscription_id=$SUBSCRIPTION_ID 
```

You should get `No changes. Your infrastructure matches the configuration.`

