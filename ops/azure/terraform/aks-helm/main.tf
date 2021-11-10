# install Terraform Azure plugin and provide it with the connection variables. The variables are defined in `variables.tf`
provider "azurerm" {

  subscription_id = var.subscription_id
  client_id       = var.service_principle_id
  client_secret   = var.service_principle_secret
  tenant_id       = var.tenant_id

  features {}
}

# embed module `cluster` and propagate the variables.
module "cluster" {

  source                    = "./modules/cluster/"
  service_principle_id      = var.service_principle_id
  service_principle_secret  = var.service_principle_secret
  location                  = var.location
  kubernetes_version        = var.kubernetes_version
  cluster_name              = var.cluster_name
  dns_cluster_prefix        = var.dns_cluster_prefix
  rg_name                   = var.rg_name

}

#  embed module `k8s` and define variables.
module "k8s" {

  source                = "./modules/k8s/"
  host                  = module.cluster.host
  client_certificate    = base64decode(module.cluster.client_certificate)
  client_key            = base64decode(module.cluster.client_key)
  cluster_ca_certificate= base64decode(module.cluster.cluster_ca_certificate)
  k8s_namespace         = var.k8s_namespace

}
