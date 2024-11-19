package com.vscoding.app.logsplitter.control;

import com.vscoding.app.logsplitter.bean.LogEntry;
import com.vscoding.app.logsplitter.bean.LogSplitterPattern;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogSplitter {
  private static final String PATH = "/Users/vladislav.samojlenko/Documents/Logs/cae-logs/";
  private static final String PATH_SPLIT = "/Users/vladislav.samojlenko/Documents/Logs/cae-logs/split/";

  private final LogPatternRecognizer logPatternRecognizer;
  private final LogEntryParser logEntryParser;

  @PostConstruct
  public void execute() {
    var folder = new File(PATH);

    if (!folder.exists() || !folder.isDirectory()) {
      log.error("Folder {} does not exist or is not a directory", PATH);
    }

    var logs = folder.listFiles();

    if (logs == null) {
      log.error("No files found in folder {}", PATH);
      return;
    }

    var collect = Arrays.stream(logs)
            .map(this::processFile)
            .toList();
    var splittedLogs = merge(collect);

    var splitFolder = new File(PATH_SPLIT);

    if (!splitFolder.exists()) {
      splitFolder.mkdirs();
    }

    Arrays.stream(splitFolder.listFiles()).forEach(File::delete);


    splittedLogs.forEach((name, entries) -> {
      var file = new File(PATH_SPLIT + name + ".log");
      try {
        var lines = entries.stream().map(LogEntry::lines)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        FileUtils.writeLines(file, lines);
      } catch (Exception e) {
        log.error("Error writing to file: {}", file.getName(), e);
      }
    });
  }

  private Map<String, List<LogEntry>> merge(List<Map<String, List<LogEntry>>> entries) {
    var result = new HashMap<String, List<LogEntry>>();

    entries.forEach(map ->
            map.forEach((key, value) -> {
              if (!result.containsKey(key)) {
                result.put(key, new ArrayList<>());
              }

              result.get(key).addAll(value);
            }));

    return result;
  }

  private Map<String, List<LogEntry>> processFile(File file) {
    var result = new HashMap<String, List<LogEntry>>();

    if (!file.isFile() || !file.getName().endsWith(".log")) {
      log.info("File {} is not a log file", file.getName());
      return result;
    }

    log.info("Processing file: {}", file.getName());
    try {
      List<LogSplitterPattern> selectedPattern = new ArrayList<>();

      var lines = FileUtils.readLines(file, "UTF-8");
      log.info("Read {} lines from file {}", lines.size(), file.getName());

      var entries = logEntryParser.parse(lines, file.getName());
      log.info("Mapped {} log entries from file {}", entries.size(), file.getName());

      for (var entry : entries) {
        var patterns = logPatternRecognizer.recognize(entry, selectedPattern);
        selectedPattern = patterns.pattern();

        selectedPattern.forEach(pattern -> {
          if (!result.containsKey(pattern.logName())) {
            result.put(pattern.logName(), new ArrayList<>());
          }

          result.get(pattern.logName()).add(entry);
        });
      }
      log.info("Processed file: {}", file.getName());
    } catch (Exception e) {
      log.error("Error processing file: {}", file.getName(), e);
    }

    return result;
  }
}
