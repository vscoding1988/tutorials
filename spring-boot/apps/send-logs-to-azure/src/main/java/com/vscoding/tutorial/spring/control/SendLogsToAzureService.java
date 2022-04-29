package com.vscoding.tutorial.spring.control;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Will parse logs and send them as JSON to azure using the given connectionString
 */
@Slf4j
@Service
public class SendLogsToAzureService {

  private final AzureClient client;
  private final AzureLogParser parser;

  private final String logPath;

  public SendLogsToAzureService(
          AzureClient client,
          AzureLogParser parser,
          @Value("${app.log.path}") String logPath) {
    this.client = client;
    this.parser = parser;
    this.logPath = logPath;
  }

  /**
   * Send logs to azure
   */
  public void sendLogs() {
    log.info("Start log sending");

    try {
      var logs = parser.parseLogs(logPath);
      log.info("{} log entries found and parsed", logs.size());

      client.sendLogs(getLogName(), new Gson().toJson(logs));
    } catch (Exception e) {
      log.error("Could not parse logs", e);
    }
  }

  /**
   * Get log name from log file path
   *
   * @return processed log name
   */
  private String getLogName() {
    var segments = logPath.split("/");

    var fileName = segments[segments.length - 1];

    // only [a-zA-Z0-9_] are allowed
    fileName = fileName.replaceAll("[^a-zA-Z0-9_]", "_");

    // Max length is capped to 100
    if (fileName.length() >= 100) {
      fileName = fileName.substring(0, 99);
    }

    return fileName;
  }
}
