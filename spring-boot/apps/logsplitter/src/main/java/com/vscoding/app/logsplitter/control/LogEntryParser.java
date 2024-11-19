package com.vscoding.app.logsplitter.control;

import com.vscoding.app.logsplitter.bean.LogEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Service
public class LogEntryParser {
  private final Map<String, List<String>> multilinePatterns;
  private final List<Pattern> whitelistPatterns;

  public LogEntryParser(
          @Value("#{${logsplitter.logentry.map}}") Map<String, List<String>> multilinePatterns,
          @Value("${logsplitter.logentry.start.whitelist}") List<String> whitelistPatterns) {
    this.multilinePatterns = multilinePatterns;
    this.whitelistPatterns = whitelistPatterns.stream().map(Pattern::compile).toList();
  }

  public List<LogEntry> parse(List<String> lines, String logName) {
    var result = new ArrayList<LogEntry>();

    for (var index = 0; index < lines.size();) {
      var entry = parse(index, lines, logName);

      if (entry == null) {
        index++;
        continue;
      }

      index += entry.lines().size();
      result.add(entry);
    }

    return result;
  }

  private LogEntry parse(Integer index, List<String> lines, String logName) {
    var line = lines.get(index);

    if (line.trim().isEmpty()) {
      return null;
    }

    for (var entry : multilinePatterns.entrySet()) {
      var matches = lineMatches(line, Collections.singleton(entry.getKey()));

      if (matches) {
        var resultLines = new ArrayList<String>();
        resultLines.add(line);

        var result = new LogEntry(resultLines, entry.getKey(), logName);

        for (var i = index + 1; i < lines.size(); i++) {
          var currentLine = lines.get(i);

          if (lineMatches(currentLine, entry.getValue())) {
            resultLines.add(currentLine);
          } else if (includeLine(currentLine)) {
            log.debug("Line '{}' does not match {} pattern anymore, stopping", currentLine, entry.getKey());
            break;
          }
        }

        return result;
      }
    }

    if (!includeLine(line)) {
      return null;
    }

    return new LogEntry(Collections.singletonList(line), LogEntry.LINE, logName);
  }

  private boolean lineMatches(String line, Collection<String> patterns) {
    var trimmedLine = line.trim();
    return patterns.stream().anyMatch(trimmedLine::startsWith);
  }

  private boolean includeLine(String line) {
    if (line.trim().isEmpty()) {
      return false;
    }

    if (whitelistPatterns.isEmpty()) {
      return true;
    }

    return whitelistPatterns.stream().anyMatch(p -> p.matcher(line).matches());
  }
}
