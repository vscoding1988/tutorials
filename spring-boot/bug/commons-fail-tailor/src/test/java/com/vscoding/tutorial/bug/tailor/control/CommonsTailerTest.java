package com.vscoding.tutorial.bug.tailor.control;

import static org.junit.jupiter.api.Assertions.*;

import com.vscoding.tutorial.bug.tailor.utils.LogGenerator;
import org.junit.jupiter.api.Test;

class CommonsTailerTest {

  @Test
  void testCommonsTailor() throws Exception{
    // given
    var filePath = this.getClass().getResource(LogGenerator.PATH).getPath();
    var commonsTailor = new CommonsTailer(filePath, LogGenerator.PATTERN);

    // when
    commonsTailor.run();

    // then
    while (!commonsTailor.getListener().isFinished()){
      Thread.sleep(200);
    }
    assertEquals(0, commonsTailor.getListener().getBrokenLines().size());
  }
}
