package com.vscoding.tutorial.azure.control;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.vscoding.tutorial.azure.exception.BlobExistsException;
import com.vscoding.tutorial.azure.exception.BlobNotFoundException;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service to abstract azure storage operations
 */
@Service
@AllArgsConstructor
public class AzureBlobConnector {

  private final BlobContainerClient blobContainerClient;

  /**
   * Upload file to the azure storage
   *
   * @param fileName name of the file
   * @param fileBody file body
   */
  public void upload(String fileName, byte[] fileBody) {
    var blobClient = blobContainerClient.getBlobClient(fileName);

    // We don't want to override an existing blob
    if (Boolean.TRUE.equals(blobClient.exists())) {
      throw new BlobExistsException(fileName);
    }

    blobClient.upload(BinaryData.fromBytes(fileBody));
  }

  /**
   * Download the file from azure storage
   *
   * @param fileName name of the file
   * @return file body as byte array
   */
  public byte[] download(String fileName) {
    var blobClient = blobContainerClient.getBlobClient(fileName);

    if (Boolean.FALSE.equals(blobClient.exists())) {
      throw new BlobNotFoundException(fileName);
    }

    var binaryData = blobClient.downloadContent();
    return binaryData.toBytes();
  }

  /**
   * Get url for the file, the url is created with expiration of one hour
   *
   * @param fileName name of the file
   * @return file url
   */
  public String getUrl(String fileName) {
    var blobClient = blobContainerClient.getBlobClient(fileName);

    if (Boolean.FALSE.equals(blobClient.exists())) {
      throw new BlobNotFoundException(fileName);
    }

    var blobSasPermission = new BlobSasPermission().setReadPermission(true);
    var expirationDate = OffsetDateTime.now().plusHours(1);

    var values =
            new BlobServiceSasSignatureValues(expirationDate, blobSasPermission)
                    .setStartTime(OffsetDateTime.now())
                    .setContentDisposition("attachment; filename=" + fileName);

    return blobClient.getBlobUrl() + "?" + blobClient.generateSas(values);
  }

  /**
   * Delete file from azure storage
   *
   * @param fileName name of the file
   */
  public void delete(String fileName) {
    var blobClient = blobContainerClient.getBlobClient(fileName);

    if (Boolean.FALSE.equals(blobClient.exists())) {
      throw new BlobNotFoundException(fileName);
    }

    // We can also use deleteIfExists if we don't care if blob has existed
    blobClient.delete();
  }
}
