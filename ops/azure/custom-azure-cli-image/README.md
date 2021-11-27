# Terraform Image
Here you have the Dockerfile for Azure CLI image with installed terraform and kubectl. 

You can build the image by executing:
```shell
docker build -t vs/custom-azure-cli .
```
This will push the image to your local docker repo. To start the image create a `.env` file. The file will contain our subscription/tenant id and the credentials. 
```text
SERVICE_PRINCIPAL=<change>
SERVICE_PRINCIPAL_SECRET=<change>
TENANT_ID=<change>
SUBSCRIPTION_ID=<change>
```
Now you can start the image with
```shell
docker run -it --rm -v ${PWD}:/work -w /work --env-file .env vs/custom-azure-cli bash

```
