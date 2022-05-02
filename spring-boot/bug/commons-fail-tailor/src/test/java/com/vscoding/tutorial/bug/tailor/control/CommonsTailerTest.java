package com.vscoding.tutorial.bug.tailor.control;

import static org.junit.jupiter.api.Assertions.*;

import com.vscoding.tutorial.bug.tailor.utils.LogGenerator;
import org.junit.jupiter.api.Test;

class CommonsTailerTest {

  @Test
  void testCommonsTailor() {
    // given
    var filePath = this.getClass().getResource(LogGenerator.PATH).getPath();
    var commonsTailor = new CommonsTailer(filePath, LogGenerator.PATTERN);

    // when
    var brokenLines = commonsTailor.run();

    // then
    assertEquals(0,brokenLines.size());
  }
}
