package com.vscoding.app.logsplitter.bean;

import java.util.List;

public record LogPatternRecognitionResponse(List<LogSplitterPattern> pattern, boolean multiline) {
  public LogPatternRecognitionResponse(List<LogSplitterPattern> pattern) {
    this(pattern,false);
  }
}
