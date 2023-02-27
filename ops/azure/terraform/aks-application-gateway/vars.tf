variable "environment_config" {
  description = "Configuration for environment"
  type        = object({
    project_name   = string
    env_short      = string
    location_short = string
    location_name  = string
  })
  default = {
    project_name   = "aks-ag-connection"
    env_short      = "dev"
    location_short = "weu"
    location_name  = "West Europe"
  }
}

variable "network" {
  description = "Configure network"
  type        = object({
    spoke_address_space     = string
    spoke_aks_address_space = string
    spoke_aks_lb_address    = string
    spoke_agw_address_space = string
    spoke_lb_address_space  = string
  })
  default = {
    spoke_address_space     = "192.168.0.0/16"
    spoke_aks_address_space = "192.168.1.0/24"
    spoke_aks_lb_address    = "192.168.3.200"
    spoke_agw_address_space = "192.168.2.0/24"
    spoke_lb_address_space  = "192.168.3.0/24"
  }
}
