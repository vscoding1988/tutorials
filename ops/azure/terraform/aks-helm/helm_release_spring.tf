# this will read our spring boot folder
resource "helm_release" "spring" {
  name        = "spring"
  chart       = "./charts/spring"
  namespace   = "default"
  max_history = 3
  description = "Base release for spring"

  values = [
    file("./charts/spring-demo-values.yml")
  ]
}
