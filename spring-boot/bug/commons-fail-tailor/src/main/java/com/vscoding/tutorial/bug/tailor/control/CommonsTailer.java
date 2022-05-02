package com.vscoding.tutorial.bug.tailor.control;

import java.io.File;
import java.util.List;
import org.apache.commons.io.input.Tailer;

public class CommonsTailer {

  private final String path;
  private final String pattern;

  public CommonsTailer(String path, String pattern) {
    this.path = path;
    this.pattern = pattern;
  }

  public List<String> run() {
    var listener = new CommonsListener(pattern);
    var tailer = Tailer.create(new File(path), listener);
    listener.setTailer(tailer);

    tailer.run();

    return listener.getBrokenLines();
  }
}
