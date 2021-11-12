# Terraform - AKS Helm
## Goal
- deploy apps to kubernetes cluster in Azure with helm
## Prerequisites
- [Terraform - AKS](../aks/README.md)
## Set Up
Let's copy the workspace of the `aks` tutorial, so we have the terraform files needed to create a cluster. 
We will use the [custom-azure-cli-image](../../custom-azure-cli-image) in this tutorial. 
The Image uses the Azure CLI as base image but installs all the tools we need and some aliases. 
In this tutorial I will also use my aliases, so if you use Azure CLI with different Image, or locally without docker, just copy & paste the aliases from the [Dockerfile](../../custom-azure-cli-image/Dockerfile). 

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
The next step would be to initialise terraform and run a plan command.
```shell
# Init our modules 
terraform init

# Execute plan alias, to check if our config works
ter-plan
 
# Expected output 'Plan: 3 to add, 0 to change, 0 to destroy.'
```

## Helm
We have a working project so far (good time to commit our code) and now we can introduce helm to it. 
We will create in [k8s.tf](./modules/k8s/k8s.tf) the `helm provider` and wrap it around the `kubernetes provider`

```terraform
# create kubernetes with helm
provider "helm" {
  kubernetes {
    host                   =  var.host
    client_certificate     =  var.client_certificate
    client_key             =  var.client_key
    cluster_ca_certificate =  var.cluster_ca_certificate
  }
}
```
### Helm - nginx

In our first approach we want to deploy nginx to our kubernetes, so let's create a `helm_release_nginx.tf` on the root level.

```terraform
resource "helm_release" "nginx_ingress" {
  name       = "nginx-ingress"
  repository = "https://helm.nginx.com/stable"
  chart      = "nginx-ingress"
  wait       = "true"
}
```
