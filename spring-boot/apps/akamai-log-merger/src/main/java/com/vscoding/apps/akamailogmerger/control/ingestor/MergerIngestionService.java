package com.vscoding.apps.akamailogmerger.control.ingestor;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Service to merge akamai logs together and upload them back to akamai
 */
@Slf4j
@Service
public class MergerIngestionService implements Ingestor {


  private final BlobContainerClient blobContainerClient;
  private final String targetPath;
  private final boolean deleteAfterMerge;
  private final boolean activate;

  public MergerIngestionService(
          BlobContainerClient blobContainerClient,
          @Value("${application.mode.delete}") boolean deleteAfterMerge,
          @Value("${application.ingestion.merge}") boolean activate,
          @Value("${application.storage.targetPath}") String logTargetPath) {
    this.blobContainerClient = blobContainerClient;
    this.targetPath = logTargetPath;
    this.deleteAfterMerge = deleteAfterMerge;
    this.activate = activate;
  }

  @Override
  public void ingest(Date date, String logData, List<BlobItem> blobItems) {
    if (!activate) {
      return;
    }

    zipAndUploadLog(date, logData);

    if (deleteAfterMerge) {
      log.info("Start cleanup of source logs '{}'", date);

      blobItems.parallelStream().forEach(blobItem -> {
        var blobClient = blobContainerClient.getBlobClient(blobItem.getName());
        blobClient.delete();
      });
    }
  }

  /**
   * Zips the log content and uploads it to the target path with the date as part of the filename
   *
   * @param key        the date key
   * @param logContent the log content to zip and upload
   */
  private void zipAndUploadLog(Date key, String logContent) {
    try {
      var targetBlobClient = getBlobForDate(key);

      if (targetBlobClient == null) {
        log.error("No available blob found for date: {}", key);
        return;
      }

      var mergedLog = "";

      if (Boolean.TRUE.equals(targetBlobClient.exists())) {
        var gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(targetBlobClient.downloadContent().toBytes()));
        mergedLog = new String(gzipInputStream.readAllBytes());
      }

      mergedLog += logContent;

      var obj = new ByteArrayOutputStream();
      var gzip = new GZIPOutputStream(obj);
      gzip.write(mergedLog.getBytes(StandardCharsets.UTF_8));
      gzip.close();


      targetBlobClient.upload(new ByteArrayInputStream(obj.toByteArray()), true);
      log.info("Uploaded merged log for date: {} to blob: {}", key, targetBlobClient.getBlobUrl());
    } catch (Exception e) {
      log.error("Error zipping file for: {}", key, e);
    }
  }

  private BlobClient getBlobForDate(Date date) {
    var path = new SimpleDateFormat("yyyy/MM/dd").format(date);

    for (int i = 0; i < 2000; i++) {
      var name = String.format("%s/%s/logs-%tF-%02d.log.gz", targetPath, path, date, i);
      var client = blobContainerClient.getBlobClient(name);

      if (!Boolean.TRUE.equals(client.exists())) {
        return client;
      }
    }

    return null;
  }
}
