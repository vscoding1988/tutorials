FROM mcr.microsoft.com/azure-cli

# Set variables

ENV SERVICE_PRINCIPAL="empty"
ENV SERVICE_PRINCIPAL_SECRET="empty"
ENV TENANT_ID="empty"
ENV SUBSCRIPTION_ID="empty"

# Install Terraform
RUN curl -o /tmp/terraform.zip -LO https://releases.hashicorp.com/terraform/1.0.10/terraform_1.0.10_linux_amd64.zip
RUN unzip /tmp/terraform.zip
RUN chmod +x terraform && mv terraform /usr/local/bin/

# Install KubeCtl
RUN curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
RUN chmod +x ./kubectl
RUN mv ./kubectl /usr/local/bin/kubectl

# Add aliase
RUN alias ter-apply="terraform apply -var service_principle_id=$SERVICE_PRINCIPAL -var service_principle_secret=$SERVICE_PRINCIPAL_SECRET -var tenant_id=$TENANT_ID  -var subscription_id=$SUBSCRIPTION_ID"
RUN alias ter-plan="terraform plan -var service_principle_id=$SERVICE_PRINCIPAL -var service_principle_secret=$SERVICE_PRINCIPAL_SECRET -var tenant_id=$TENANT_ID  -var subscription_id=$SUBSCRIPTION_ID"
RUN alias ter-destroy="terraform destroy -var service_principle_id=$SERVICE_PRINCIPAL -var service_principle_secret=$SERVICE_PRINCIPAL_SECRET -var tenant_id=$TENANT_ID  -var subscription_id=$SUBSCRIPTION_ID"
RUN alias az-login="az login --service-principal -u $SERVICE_PRINCIPAL -p $SERVICE_PRINCIPAL_SECRET --tenant $TENANT_ID"

