package com.vscoding.apps.akamailogmerger.control;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.ListBlobsOptions;
import com.vscoding.apps.akamailogmerger.control.ingestor.Ingestor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Service to merge Akamai logs stored in Azure Blob Storage
 */
@Slf4j
@Service
public class AkamaiLogProcessor {

  private final BlobContainerClient blobContainerClient;
  private final List<Ingestor> ingestionService;
  private final AsnEnrichmentService enrichmentService;
  private final String sourcePath;

  public AkamaiLogProcessor(
          BlobContainerClient blobContainerClient,
          List<Ingestor> ingestionService,
          AsnEnrichmentService enrichmentService,
          @Value("${application.storage.sourcePath}") String logSourcePath) {
    this.blobContainerClient = blobContainerClient;
    this.ingestionService = ingestionService;
    this.sourcePath = logSourcePath;
    this.enrichmentService = enrichmentService;
  }

  @PostConstruct
  public void mergeLogs() {
    log.info("Reading logs from '{}'", sourcePath);
    var options = new ListBlobsOptions();
    options.setPrefix(sourcePath);

    var blobs = blobContainerClient.listBlobs(options, null);

    log.info("Starting to process blobs in source path: {}", sourcePath);

    // First we need to merge the logs for each day, to make it easier I will just work with the last modified date
    var sortedBlobsByDay = new HashMap<Date, List<BlobItem>>();

    blobs.stream()
            .filter(blobItem -> blobItem.getName().startsWith(sourcePath))
            .forEach(blobItem -> {
              var lastModified = blobItem.getProperties().getLastModified();
              var day = Date.from(lastModified.toInstant().truncatedTo(ChronoUnit.DAYS));
              sortedBlobsByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(blobItem);

              if (sortedBlobsByDay.get(day).size() % 7500 == 0) {
                processBlobsChunk(day, sortedBlobsByDay.get(day));
                sortedBlobsByDay.remove(day);
              }
            });

    // Handle the leftovers
    sortedBlobsByDay.forEach(this::processBlobsChunk);
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

    var dayLog = enrichmentService.enrich(dayLogsString.toString());

    ingestionService.forEach(s -> s.ingest(key, dayLog, blobItems));
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
}
