package com.vscoding.tutorial.azure.control;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * This class can be used for all integration tests, it will set up azurite and override the
 * properties, if the application.properties were changed
 */
public abstract class BaseIntegrationTest {

  static final GenericContainer<?> AZURITE_CONTAINER = new GenericContainer<>(
          DockerImageName.parse("mcr.microsoft.com/azure-storage/azurite"))
          .withCommand("azurite-blob --blobHost 0.0.0.0 --blobPort 10000")
          .withExposedPorts(10000);

  /*
   * Static container are created once per test suite
   */
  static {
    AZURITE_CONTAINER.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    var blobEndpoint = "http://"
            + AZURITE_CONTAINER.getHost() + ":"
            + AZURITE_CONTAINER.getMappedPort(10000) + "/devstoreaccount1";
    registry.add("azure.storage.blob-endpoint", () -> blobEndpoint);
    registry.add("azure.storage.account-name", () -> "devstoreaccount1");
    registry.add("azure.storage.account-key",
            () -> "Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==");
  }
}
