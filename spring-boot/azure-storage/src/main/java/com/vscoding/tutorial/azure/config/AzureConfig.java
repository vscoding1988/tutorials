package com.vscoding.tutorial.azure.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config for our azure storage connector
 */
@Configuration
public class AzureConfig {

  /**
   * Create connection to the blob service, this will be used to upload/download blobs.
   *
   * @param blobServiceClientBuilder builder injected by azure dependency
   * @return {@link BlobServiceClient}
   */
  @Bean
  public BlobServiceClient getBlobServiceClient(BlobServiceClientBuilder blobServiceClientBuilder) {
    return blobServiceClientBuilder.buildClient();
  }

  /**
   * Create client for a specific container name, this can be compared with a drive on a regular
   * computer.
   *
   * @param blobServiceClient {@link BlobServiceClient} connector to the storage
   * @param containerName container name we want to use
   * @return {@link BlobContainerClient} connector to container
   */
  @Bean
  public BlobContainerClient getContainerClient(
          BlobServiceClient blobServiceClient,
          @Value("${azure.storage.containerName}") String containerName) {

    var client = blobServiceClient.getBlobContainerClient(containerName);

    if (!client.exists()) {
      client.create();
    }

    return client;
  }
}
