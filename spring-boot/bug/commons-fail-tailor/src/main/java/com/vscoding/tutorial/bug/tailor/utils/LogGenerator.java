package com.vscoding.tutorial.bug.tailor.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

/**
 * Generates log line for parsing
 */
public class LogGenerator {

  public static final String PATTERN = "(?<HostIp>[0-9.-]+) - - \\[(?<Timestamp>.+)] \"(?<Method>[A-Z]+) (?<Path>.+) HTTP/1.1\"";
  public static final String PATH = "/generated.log";
  public static final int SIZE = 15000;
  public static final String LINE_BREAK = "\r";

  public static void main(String[] args) throws Exception {
    var file = new File(LogGenerator.class.getResource(PATH).getPath());
    FileUtils.write(file, generateLog(), StandardCharsets.UTF_8);
  }

  private static String generateLog() {
    var pattern = Pattern.compile(PATTERN);
    var lines = new ArrayList<String>();

    for (int i = 0; i < SIZE; i++) {
      var line = "127.0.0.1 - - [1223] \"POST /" + UUID.randomUUID() + " HTTP/1.1\"";

      if (pattern.matcher(line).matches()) {
        lines.add(line);
      }
    }
    return String.join(LINE_BREAK, lines);
  }
}
