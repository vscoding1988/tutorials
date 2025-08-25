package com.vscoding.apps.akamailogmerger;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AkamaiLogMergerConfig {

  @Bean
  public BlobContainerClient getBlobContainerClient(
          @Value("${application.storage.connectionString}") String connectionString,
          @Value("${application.storage.containerName}") String containerName) {
    return new BlobContainerClientBuilder()
            .connectionString(connectionString)
            .containerName(containerName)
            .buildClient();
  }
}
