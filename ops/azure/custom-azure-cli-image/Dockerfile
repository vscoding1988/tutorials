FROM mcr.microsoft.com/azure-cli

# Set variables

ENV SERVICE_PRINCIPAL_ID="empty"
ENV SERVICE_PRINCIPAL_SECRET="empty"
ENV TENANT_ID="empty"
ENV SUBSCRIPTION_ID="empty"
RUN export KUBE_CONFIG_PATH=~/.kube/config

# Install Terraform
RUN curl -o /tmp/terraform.zip -LO https://releases.hashicorp.com/terraform/1.1.5/terraform_1.1.5_linux_amd64.zip
RUN unzip /tmp/terraform.zip
RUN chmod +x terraform && mv terraform /usr/local/bin/

# Install Terraform docs
RUN curl -Lo ./terraform-docs.tar.gz https://github.com/terraform-docs/terraform-docs/releases/download/v0.16.0/terraform-docs-v0.16.0-$(uname)-amd64.tar.gz
RUN tar -xzf terraform-docs.tar.gz
RUN chmod +x terraform-docs
RUN mv terraform-docs /usr/local/bin/terraform-docs
