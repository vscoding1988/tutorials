package com.vscoding.app.logsplitter.bean;

import java.util.List;

public record LogEntry(List<String> lines, String type, String logName) {
  public static final String LINE = "line";
}
