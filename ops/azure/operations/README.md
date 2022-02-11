# Azure - CLI Operations
## KeyVault

### Purge
[Azure Doc](https://docs.microsoft.com/de-de/cli/azure/keyvault?view=azure-cli-latest#az_keyvault_purge)

If you need to remove KeyVault (f.e. because you are in an access deadlock in terraform) it is not sufficient to just delete it,
you will also need to purge it.

```shell
az keyvault purge --name=kv-temp-dev --location=westeurope --no-wait --subscription=$ARM_SUBSCRIPTION_ID
```

### Read Secrets
[Azure Doc](https://docs.microsoft.com/de-de/cli/azure/keyvault/secret?view=azure-cli-latest#az-keyvault-secret-show)
```shell
az keyvault secret show --name <secret_name> --vault-name <kv_name> --output tsv
```

### Remove Secrets

[Azure Doc](https://docs.microsoft.com/de-de/cli/azure/keyvault/secret?view=azure-cli-latest#az-keyvault-secret-delete)

```shell
az keyvault secret delete --name <secret_name> --vault-name <kv_name>
```

## How to switch Docker image TAG for running Application Service
[Azure Doc](https://docs.microsoft.com/en-us/cli/azure/webapp/config/container?view=azure-cli-latest#az_webapp_config_container_set)

Use case: We have a pipeline for building a Docker Image and pushing it to our repository.
To deploy the Image to azure Application Service we need to change the Docker Image TAG Config.

```shell
az webapp config container set --docker-custom-image-name <full_image_path>:<new_tag> --name <app_service_name> --resource-group  <rg_name>
az webapp restart --name <app_service_name> --resource-group <rg_name>
```

## Storage Account - get connection string
[Azure Doc](https://docs.microsoft.com/de-de/cli/azure/storage/account?view=azure-cli-latest#az_storage_account_show_connection_string)

To get storage account `connectionString` (f.e. to deploy something to it from Jenkins) you can use `az acccount show-connection-string`.

```shell
# Login as service_principle (or with 'az login' with own user)
az login --service-principal -u ${AZURE_CLIENT_ID} -p ${AZURE_CLIENT_SECRET} --tenant ${AZURE_TENANT_ID}

# Select the Subscription your storage account is in
az account set --subscription ${AZURE_SUBSCRIPTION_ID}

# Get storage account by name/rg and read primary connection
az storage account show-connection-string -g rg-app-dev -n stappdev --key primary          
```
Result:
```json
{
  "connectionString": "DefaultEndpointsProtocol=https;EndpointSuffix=core.windows.net;AccountName=XXXX;AccountKey=XXXX"
}
```

