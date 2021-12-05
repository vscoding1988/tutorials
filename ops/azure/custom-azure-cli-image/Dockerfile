FROM mcr.microsoft.com/azure-cli

# Set variables

ENV SERVICE_PRINCIPAL_ID="empty"
ENV SERVICE_PRINCIPAL_SECRET="empty"
ENV TENANT_ID="empty"
ENV SUBSCRIPTION_ID="empty"
RUN export KUBE_CONFIG_PATH=~/.kube/config

# Install Terraform
RUN curl -o /tmp/terraform.zip -LO https://releases.hashicorp.com/terraform/1.0.11/terraform_1.0.11_linux_amd64.zip
RUN unzip /tmp/terraform.zip
RUN chmod +x terraform && mv terraform /usr/local/bin/

# Install Terraform docs
RUN curl -Lo ./terraform-docs.tar.gz https://github.com/terraform-docs/terraform-docs/releases/download/v0.16.0/terraform-docs-v0.16.0-$(uname)-amd64.tar.gz
RUN tar -xzf terraform-docs.tar.gz
RUN chmod +x terraform-docs
RUN mv terraform-docs /usr/local/terraform-docs

# Install KubeCtl
RUN curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
RUN chmod +x ./kubectl
RUN mv ./kubectl /usr/local/bin/kubectl

# Install Helm
RUN curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
RUN chmod 700 get_helm.sh
RUN ./get_helm.sh
