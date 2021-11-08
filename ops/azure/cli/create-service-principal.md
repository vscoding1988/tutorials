# How to create a service principal
## How to
```shell
#Create and save `appId` and `password`
az ad sp create-for-rbac --skip-assignment --name <name> -o json

SERVICE_PRINCIPAL=<appId>
SERVICE_PRINCIPAL_SECRET=<password>
```

To assign him as contributor over a certain resource group use

```shell
az role assignment create --assignee $SERVICE_PRINCIPAL \
--scope "/subscriptions/<subscriptionId>/resourceGroups/<resourceGroupName>" \
--role <role>
```
## Example

Create resource group with name `rg-example` and principle with name `rg-example-sp` You have to be logged in and provide your Subscription ID. 

```shell
RESOURCE_GROUP=rg-example
SUBSCRIPTION_ID=<your-subscriptionId>

# To get all location list use `az account list-locations`
az group create -n $RESOURCE_GROUP -l westeurope

SERVICE_PRINCIPAL_JSON=$(az ad sp create-for-rbac --skip-assignment --name ${RESOURCE_GROUP}-sp -o json)

SERVICE_PRINCIPAL=$(echo $SERVICE_PRINCIPAL_JSON | jq -r '.appId')
SERVICE_PRINCIPAL_SECRET=$(echo $SERVICE_PRINCIPAL_JSON | jq -r '.password')

az role assignment create --assignee $SERVICE_PRINCIPAL \
--scope "/subscriptions/$SUBSCRIPTION_ID/resourceGroups/$RESOURCE_GROUP" \
--role Contributor
```
