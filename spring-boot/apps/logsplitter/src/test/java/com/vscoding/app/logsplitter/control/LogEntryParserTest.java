package com.vscoding.app.logsplitter.control;

import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogEntryParserTest {
  private final LogEntryParser sut = new LogEntryParser(Map.of(
          "java.net.", List.of("at ", "...", "Caused by:"),
          "com.coremedia.", List.of("at ", "...", "Caused by:")
  ), Collections.emptyList());

  @Test
  void testSingleLines() throws Exception {
    // Given
    var logStream = this.getClass().getResourceAsStream("/LogEntryParser/single-line.log");
    var log = StreamUtils.copyToString(logStream, StandardCharsets.UTF_8);
    var logLines = log.lines().toList();

    // When
    var result = sut.parse(logLines, "single-line.log");

    // Then
    assertEquals(logLines.size(), result.size());
  }

  @Test
  void testStacktraceLines() throws Exception {
    // Given
    var logStream = this.getClass().getResourceAsStream("/LogEntryParser/stacktrace.log");
    var log = StreamUtils.copyToString(logStream, StandardCharsets.UTF_8);
    var logLines = log.lines().toList();

    // When
    var result = sut.parse(logLines, "stacktrace.log");

    // Then
    assertEquals(2, result.size());
  }

  @Test
  void testMixedLogLines() throws Exception {
    // Given
    var logStream = this.getClass().getResourceAsStream("/LogEntryParser/mixed.log");
    var log = StreamUtils.copyToString(logStream, StandardCharsets.UTF_8);
    var logLines = log.lines().toList();

    // When
    var result = sut.parse(logLines, "mixed.log");

    // Then
    assertEquals(6, result.size());
  }
}
