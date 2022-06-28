package com.vscoding.tutorial.azure.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.vscoding.tutorial.azure.exception.BlobExistsException;
import com.vscoding.tutorial.azure.exception.BlobNotFoundException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AzureBlobConnectorTest extends BaseIntegrationTest {

  private static final String FILE_NAME = "tempFile.txt";
  private static final byte[] FILE_BODY = "This is file body".getBytes(StandardCharsets.UTF_8);
  @Autowired
  AzureBlobConnector sut;

  @Autowired
  BlobContainerClient blobContainerClient;

  @Test
  @DisplayName("Upload of the file should be successful")
  void upload() {
    // When
    sut.upload(FILE_NAME, FILE_BODY);

    // Then
    var uploadedBlob = blobContainerClient.getBlobClient(FILE_NAME);

    assertTrue(uploadedBlob.exists());
    assertEquals(new String(FILE_BODY), new String(uploadedBlob.downloadContent().toBytes()));
  }

  @Test
  @DisplayName("Upload of the file should fail, when the file already exists")
  void upload_failure() {
    // given
    sut.upload(FILE_NAME, FILE_BODY);

    // When
    assertThrows(BlobExistsException.class, () -> sut.upload(FILE_NAME, FILE_BODY));
  }

  @Test
  @DisplayName("Download of the file should be successful")
  void download() {
    // Given
    sut.upload(FILE_NAME, FILE_BODY);

    // When
    var fileBody = sut.download(FILE_NAME);

    // Then
    assertEquals(new String(FILE_BODY), new String(fileBody));
  }

  @Test
  @DisplayName("Download of the file should fail, when the file do not exists")
  void download_notFound() {
    // When
    assertThrows(BlobNotFoundException.class, () -> sut.download(FILE_NAME));
  }

  @Test
  @DisplayName("Delete of the file should be successful")
  void delete() {
    // Given
    sut.upload(FILE_NAME, FILE_BODY);

    // When
    sut.delete(FILE_NAME);

    // Then
    assertFalse(blobContainerClient.getBlobClient(FILE_NAME).exists());
  }

  @Test
  @DisplayName("Delete of the file should fail, when the file do not exists")
  void delete_notFound() {
    // When
    assertThrows(BlobNotFoundException.class, () -> sut.delete(FILE_NAME));
  }

  @Test
  @DisplayName("Getting of the file-url should be successful")
  void getUrl() {
    // Given
    sut.upload(FILE_NAME, FILE_BODY);

    // When
    var url = sut.getUrl(FILE_NAME);

    // Then
    assertNotNull(url);
    assertFalse(url.isEmpty());
  }

  @Test
  @DisplayName("Getting of the file-url should fail, when the file do not exists")
  void getUrl_notFound() {
    // When
    assertThrows(BlobNotFoundException.class, () -> sut.getUrl(FILE_NAME));
  }

  /**
   * We need to execute cleanup, because we are not restarting the Azurite after each execution.
   */
  @AfterEach
  void tearDown() {
    var blobClient = blobContainerClient.getBlobClient(FILE_NAME);

    if(blobClient.exists()){
      blobClient.delete();
    }
  }
}
