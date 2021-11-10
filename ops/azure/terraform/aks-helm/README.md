# Terraform - AKS Helm
## Goal
- deploy apps to kubernetes cluster in Azure with helm
## Prerequisites
- [Terraform - AKS](../aks/README.md)
## Set Up
Let's copy the workspace of the `aks` tutorial, so we have the terraform files needed to create a cluster. 
We will use the [custom-azure-cli-image](../../custom-azure-cli-image) in this tutorial, it uses the Azure CLI image but installs all the tools we will need.

### Project Structure
```text
deploy-helm
    modules
        cluster
            cluster.tf
            output.tf
            variables.tf
        k8s
            k8s.tf
            variables.tf
    main.tf
    variables.tf
```
