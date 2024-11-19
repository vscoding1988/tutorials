package com.vscoding.app.logsplitter.control;

import com.vscoding.app.logsplitter.bean.LogEntry;
import com.vscoding.app.logsplitter.bean.LogPatternRecognitionResponse;
import com.vscoding.app.logsplitter.bean.LogSplitterPattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogPatternRecognizer {
  private final List<LogSplitterPattern> patterns;
  private final LogSplitterPattern fallbackPattern;

  public LogPatternRecognizer(List<LogSplitterPattern> patterns,
                              @Value("${logsplitter.fallback.filename}") String fallbackFilename) {
    this.patterns = patterns;
    this.fallbackPattern = fallbackFilename.isEmpty() ? null : new LogSplitterPattern(List.of(), fallbackFilename);
  }

  public LogPatternRecognitionResponse recognize(LogEntry entry, List<LogSplitterPattern> lastPatterns) {
    if (belongsToLastPattern(entry, lastPatterns)) {
      return new LogPatternRecognitionResponse(lastPatterns, true);
    }

    return new LogPatternRecognitionResponse(getMatchingPatterns(entry));
  }

  public List<LogSplitterPattern> getMatchingPatterns(LogEntry entry) {
    var result = new ArrayList<LogSplitterPattern>();
    var joinedLines = String.join("\n", entry.lines());

    for (var pattern : patterns) {
      if (!result.isEmpty() && pattern.multiMatch() == LogSplitterPattern.Type.SINGLE_MAP){
        // We have already a match and this pattern does not allow multi match
        continue;
      }

      if (pattern.pattern().stream().anyMatch(joinedLines::contains)) {
        result.add(pattern);

        if(pattern.multiMatch() == LogSplitterPattern.Type.DISALLOW_FURTHER_MAP){
          break;
        }
      }
    }

    if (result.isEmpty() && fallbackPattern != null) {
      result.add(fallbackPattern);
    }

    return result;
  }

  private boolean belongsToLastPattern(LogEntry entry, List<LogSplitterPattern> lastPatterns) {
    if(entry.type().equals(LogEntry.LINE)) {
      return false;
    }

    var joinedLines = String.join("\n", entry.lines());

    return lastPatterns.stream()
            .anyMatch(p -> p.pattern().stream().anyMatch(joinedLines::contains));
  }
}
