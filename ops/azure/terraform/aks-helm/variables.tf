variable "service_principle_id" {
}

variable "service_principle_secret" {
}

variable "tenant_id" {
}

variable "subscription_id" {
}

variable "location" {
  default = "westeurope"
}

variable "kubernetes_version" {
  default = "1.22.2"
}


# naming conventions, change when copy
variable "rg_name" {
  default = "rg-aks-vs"
  description = "The name of the created resource group"
}

variable "cluster_name" {
  default = "cluster-aks-vs"
  description = "The name of the created cluster"
}

variable "dns_cluster_prefix" {
  default = "cluster-aks-vs"
  description = "DNS prefix of the created cluster"
}

variable "k8s_namespace" {
  default = "k8s-aks-vs"
  description = "k8s namespace"
}
