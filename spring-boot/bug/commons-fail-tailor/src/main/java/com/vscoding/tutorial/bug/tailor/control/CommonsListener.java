package com.vscoding.tutorial.bug.tailor.control;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.TailerListenerAdapter;

@Slf4j
public class CommonsListener extends TailerListenerAdapter {

  private final Pattern pattern;
  private final List<String> brokenLines = new ArrayList<>();
  private boolean finished;

  public CommonsListener(String pattern) {
    this.pattern = Pattern.compile(pattern);
  }

  @Override
  public void handle(String line) {
    var matcher = pattern.matcher(line);

    if (!matcher.matches()) {
      log.error("Broken line: '{}'", line);
      brokenLines.add(line);
    }
  }

  @Override
  public void endOfFileReached() {
    super.endOfFileReached();
    this.finished = true;
  }

  public List<String> getBrokenLines() {
    return brokenLines;
  }

  public boolean isFinished() {
    return finished;
  }
}
