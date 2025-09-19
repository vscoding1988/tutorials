package com.vscoding.apps.akamailogmerger.control;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Service to merge Akamai logs stored in Azure Blob Storage
 */
@Slf4j
@Service
public class AkamaiLogMerger {

  private final BlobContainerClient blobContainerClient;
  private final String sourcePath;
  private final String targetPath;
  private final boolean deleteAfterMerge;

  public AkamaiLogMerger(
          BlobContainerClient blobContainerClient,
          @Value("${application.mode.delete}") boolean deleteAfterMerge,
          @Value("${application.storage.sourcePath}") String logSourcePath,
          @Value("${application.storage.targetPath}") String logTargetPath) {
    this.blobContainerClient = blobContainerClient;
    this.sourcePath = logSourcePath;
    this.targetPath = logTargetPath;
    this.deleteAfterMerge = deleteAfterMerge;
  }

  @PostConstruct
  public void mergeLogs() {
    log.info("Merging logs from '{}' to '{}'", sourcePath, targetPath);
    var blobs = blobContainerClient.listBlobs();

    log.info("Starting to process blobs in source path: {}", sourcePath);

    // First we need to merge the logs for each day, to make it easier I will just work with the last modified date
    var sortedBlobsByDay = new HashMap<Date, List<BlobItem>>();

    blobs.stream()
            .filter(blobItem -> blobItem.getName().startsWith(sourcePath))
            .forEach(blobItem -> {
              var lastModified = blobItem.getProperties().getLastModified();
              var day = Date.from(lastModified.toInstant().truncatedTo(ChronoUnit.DAYS));
              sortedBlobsByDay.computeIfAbsent(day, k -> new java.util.ArrayList<>()).add(blobItem);
            });

    log.info("Starting to process blobs by day:");

    sortedBlobsByDay.forEach(this::processBlobsForDay);
  }

  /**
   * Processes and merges blobs for a specific day
   *
   * @param key       the date key
   * @param blobItems the list of blob items for that day
   */
  private void processBlobsForDay(Date key, List<BlobItem> blobItems) {
    log.info("Merging {} blobs for '{}'", blobItems.size(), key);

    var logSteps = Math.max(10000, blobItems.size() / 10);

    // Split blobs into chunks and process in parallel
    var counter = new AtomicInteger();
    blobItems.stream()
            .collect(Collectors.groupingBy(s -> counter.getAndIncrement() / logSteps))
            .values()
            .forEach(chunk -> processBlobsChunk(key, chunk));
  }

  private void processBlobsChunk(Date key, List<BlobItem> blobItems) {
    log.info("Merging chunk {} blobs for '{}'", blobItems.size(), key);

    var dayLogs = new ArrayList<String>();

    // Split blobs into chunks and process in parallel
    var dayLogsString = new StringBuilder();

    blobItems.parallelStream()
            .forEach(blobItem -> {
              var logContent = downloadAndUnzipFile(blobItem);
              dayLogs.add(logContent);
            });

    dayLogs.stream()
            .filter(logContent -> !logContent.isEmpty())
            .forEach(log -> dayLogsString.append(log).append("\n"));

    zipAndUploadLog(key, dayLogsString.toString());

    if (deleteAfterMerge) {
      log.info("Start cleanup of source logs '{}'", key);

      blobItems.parallelStream().forEach(blobItem -> {
        var blobClient = blobContainerClient.getBlobClient(blobItem.getName());
        blobClient.delete();
      });
    }
  }

  /**
   * Downloads and unzips a file from Azure Blob Storage
   *
   * @param blobItem the blob item to download and unzip
   * @return the unzipped content as a string
   */
  private String downloadAndUnzipFile(BlobItem blobItem) {
    try {
      var blobClient = blobContainerClient.getBlobClient(blobItem.getName());
      var gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(blobClient.downloadContent().toBytes()));
      return new String(gzipInputStream.readAllBytes());
    } catch (Exception e) {
      log.error("Error downloading and unzipping file: {}", blobItem.getName(), e);
      return "";
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

    for (int i = 0; i < 30; i++) {
      var name = String.format("%s/%s/logs-%tF-%02d.log.gz", targetPath, path, date, i);
      var client = blobContainerClient.getBlobClient(name);

      if (!Boolean.TRUE.equals(client.exists())) {
        return client;
      }
    }

    return null;
  }
}
