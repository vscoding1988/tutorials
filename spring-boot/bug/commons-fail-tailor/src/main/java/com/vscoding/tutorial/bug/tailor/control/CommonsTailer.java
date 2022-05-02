package com.vscoding.tutorial.bug.tailor.control;

import java.io.File;
import org.apache.commons.io.input.Tailer;

public class CommonsTailer {

  private final String path;
  private final String pattern;
  private final CommonsListener listener;

  public CommonsTailer(String path, String pattern) {
    this.path = path;
    this.pattern = pattern;
    this.listener = new CommonsListener(pattern);
  }

  public void run() {
    Tailer.create(new File(path), listener);
  }

  public CommonsListener getListener() {
    return listener;
  }
}
