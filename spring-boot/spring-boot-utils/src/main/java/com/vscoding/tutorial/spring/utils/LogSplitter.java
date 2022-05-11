package com.vscoding.tutorial.spring.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * Handy script to read a file and only keep files with a certain marker. I use it for splitting k8s
 * ingress logs by pods. Supports also multi log merged to one.
 * <p>
 * While merging the logs are just appended, there is no logic for time based merge
 */
@Slf4j
public class LogSplitter {

  /**
   * List of files which should be parsed
   */
  private static final List<String> SRC_FILE_PATHS = Arrays.asList(
          "C:/Dev/logs/src1.log",
          "C:/Dev/logs/src2.log"
  );

  /**
   * Target file path
   */
  private static final String TARGET_FILE_PATH = "C:/Dev/logs/target.log";

  /**
   * Preferred line ending
   */
  private static final String LINE_ENDING = "\n";

  /**
   * Regex pattern which each line should match to be transported to the new file
   */
  private static final Pattern FILTER_PATTERN = Pattern.compile(
          ".*find-me.*");

  public static void main(String[] args) throws Exception {
    var validLines = new ArrayList<String>();

    SRC_FILE_PATHS.stream()
            .map(LogSplitter::parsePath)
            .forEach(validLines::addAll);

    var body = String.join(LINE_ENDING, validLines);

    var path = Paths.get(TARGET_FILE_PATH);
    Files.write(path, body.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Parse file
   *
   * @param path to file
   * @return list of valid lines
   */
  public static List<String> parsePath(String path) {
    var validLines = new ArrayList<String>();

    try (var br = new BufferedReader(new FileReader(path))) {

      br.lines()
              .filter(line -> FILTER_PATTERN.matcher(line).matches())
              .forEach(validLines::add);
    } catch (Exception e) {
      log.error("Error parsing file '{}'", path, e);
    }
    return validLines;
  }
}
