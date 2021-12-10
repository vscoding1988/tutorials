# Azure - CLI Operations
## Purge KeyVault
[Azure Doc](https://docs.microsoft.com/de-de/cli/azure/keyvault?view=azure-cli-latest#az_keyvault_purge)

If you need to remove KeyVault (f.e. because you are in an access deadlock in terraform) it is not sufficient to just delete it,
you will also need to purge it.

```shell
az keyvault purge --name=kv-temp-dev --location=westeurope --no-wait --subscription=$ARM_SUBSCRIPTION_ID
```

## How to switch Docker image TAG for running Application Service
[Azure Doc](https://docs.microsoft.com/en-us/cli/azure/webapp/config/container?view=azure-cli-latest#az_webapp_config_container_set)
Use case: We have a pipeline for building a Docker Image and pushing it to our repository.
To deploy the Image to azure Application Service we need to change the Docker Image TAG Config.

```shell
az webapp config container set --docker-custom-image-name <full_image_path>:<new_tag> --name <app_service_name> --resource-group  <rg_name>
az webapp restart --name <app_service_name> --resource-group <rg_name>
```

