package com.vscoding.app.logsplitter.bean;

import java.util.List;

public record LogSplitterPattern(List<String> pattern, String logName, Type multiMatch) {
  public enum Type {
    // Allows to map lines already mapped by other patterns
    DEFAULT,
    // Do not allow to map lines already mapped by other patterns
    SINGLE_MAP,
    // Do not allow to map lines that are mapped by this pattern
    DISALLOW_FURTHER_MAP
  }

  public LogSplitterPattern(List<String> pattern, String logName) {
    this(pattern, logName, LogSplitterPattern.Type.DEFAULT);
  }
}
