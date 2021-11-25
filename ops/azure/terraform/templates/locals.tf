locals {
  naming = {
    rg_name     = "rg-${var.project_name}-${var.environment}"
    as_name     = "as-${var.project_name}-${var.environment}"
    asp_name    = "asp-${var.project_name}-${var.environment}"
    appi_name   = "appi-${var.project_name}-${var.environment}"
    loanw_name  = "loanw-${var.project_name}-${var.environment}"
  }

  docker_config = {
    url         = var.url
    username    = var.username
    password    = var.password
    image_path  = var.image_path
  }
}
