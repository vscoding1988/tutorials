# Azure Storage and Spring Boot

This application will demonstrate how to:

- create connection to azure storage
- upload a file
- download a file
- create integration test

We won't work against a real azure storage, but against a local docker instance
of [Azurite](https://hub.docker.com/_/microsoft-azure-storage-azurite) this won't matter for our
implementation because we can afterwards replace the Azurite connection string with the real one. To
start Azurite we will use [Testcontainers](https://www.testcontainers.org/).

## Azure Connection

To establish the azure connection you have to provide the following properties:

```properties
# Endpoint for blob operation 
azure.storage.blob-endpoint=http://127.0.0.1:10000/devstoreaccount1
# azure storage account key (password)
azure.storage.account-key=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==
# azure storage account name (username)
azure.storage.account-name=devstoreaccount1
```

> For integration test we would override the properties with Azurite credentials
in [BaseIntegrationTest](src/test/java/com/vscoding/tutorial/azure/control/BaseIntegrationTest.java). 

