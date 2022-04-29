package com.vscoding.tutorial.spring.control;

import com.google.gson.JsonObject;
import com.vscoding.tutorial.spring.control.dto.AzureLog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Will transform logs to Azure friendly format
 */
@Slf4j
@Service
public class AzureLogParser {

  private final Pattern logPattern;
  private final String timestampPattern;
  private final String timestampFieldName;
  private final List<String> logFields;

  public AzureLogParser(
          @Value("${app.log.fields}") List<String> logFields,
          @Value("${app.log.timestampPattern}") String timestampPattern,
          @Value("${app.log.timestampFieldName}") String timestampFieldName,
          @Value("${app.log.pattern}") String logPattern) {
    this.logPattern = Pattern.compile(logPattern);
    this.logFields = logFields;
    this.timestampPattern = timestampPattern;
    this.timestampFieldName = timestampFieldName;
  }

  /**
   * Parse logs for given location
   *
   * @return {@link  AzureLog} list
   */
  public List<JsonObject> parseLogs(String logPath) throws IOException {
    var logs = new ArrayList<JsonObject>();
    var resourceAsStream = new FileInputStream(logPath);

    try (var br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
      String line;

      while ((line = br.readLine()) != null) {
        var jsonObject = parseLine(line);

        if (jsonObject != null) {
          logs.add(jsonObject);
        }
      }
    } catch (Exception e) {
      log.error("Error parsing file '{}'", logPath, e);
    }

    return logs;
  }

  /**
   * Parse one log line
   *
   * @return logger fields
   */
  private JsonObject parseLine(String line) {
    var matcher = logPattern.matcher(line);

    if (!matcher.matches()) {
      log.error("Could not parse '{}'", line);
      return null;
    }

    var values = new JsonObject();

    for (var logField : logFields) {
      var value = matcher.group(logField);

      // change date to Azure friendly pattern
      if (logField.equals(timestampFieldName)) {
        value = getAzureDate(value);
      }

      values.addProperty(logField, value);
    }

    return values;
  }

  /**
   * Change log date to ISO 8601 format
   *
   * @param date date string with pattern defined in timestampPattern
   * @return dateString in ISO 8601 format or if parsing not possible the original value
   */
  private String getAzureDate(String date) {
    var azureDate = date;

    if (timestampPattern != null && !timestampPattern.isEmpty()) {
      try {
        var parsedDate = new SimpleDateFormat(timestampPattern, Locale.ENGLISH).parse(date);
        azureDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.ENGLISH).format(parsedDate);
      } catch (Exception e) {
        log.error("Could not parse date '{}' with pattern '{}'", date, timestampPattern);
      }
    }

    return azureDate;
  }
}
