# Azure - CLI
## Prerequisite
- [Running docker](../../docker/getting-started-windows.md)
## Set up
Easiest way to use AZURE CLI is to run it as a docker container. <br>
You can find the image on [DockerHub](https://hub.docker.com/_/microsoft-azure-cli) <br>

```shell
# Run AZURE CLI
# -v ~/mount/azure:/work will mount the folder below user/mount/azure/ as volume, you should select a folder where you have all of your scripts
docker run -it --rm -v ${PWD}:/work -w /work --entrypoint /bin/sh mcr.microsoft.com/azure-cli
```

## Login
```shell
az login 
```
You can now use device login mechanics for authentication. You will get a URL and a code to enter. After login you will get JSON Object for each of your subscriptions.

```json
[
  {
    "cloudName": "AzureCloud",
    "homeTenantId": "homeTenantId",
    "id": "id",
    "isDefault": true,
    "managedByTenants": [],
    "name": "Subscription Name",
    "state": "Enabled",
    "tenantId": "tenantId",
    "user": {
      "name": "email",
      "type": "user"
    }
  }
]
```
Make sure to copy the `tenantId` for further use and set it as environment variable. This will make everything easier for terraform.

```shell
TENTANT_ID=<tenantId> 
```

Next we will need your subscription ID and save it also as an environment variable.

```shell
az account list -o table
SUBSCRIPTION_ID=<SubscriptionId>
```

Also, just to make sure that we are working on a specific subscription we want to select it manually.

```shell
az account set --subscription $SUBSCRIPTION_ID
```
