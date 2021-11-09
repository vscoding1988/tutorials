# create a namespace for our kubernetes
provider "kubernetes" {
  host                   =  var.host
  client_certificate     =  var.client_certificate
  client_key             =  var.client_key
  cluster_ca_certificate =  var.cluster_ca_certificate
}

# create a namespace for our kubernetes
resource "kubernetes_namespace" "k8s_namespace" {
  metadata {
    name = var.k8s_namespace
  }
}

