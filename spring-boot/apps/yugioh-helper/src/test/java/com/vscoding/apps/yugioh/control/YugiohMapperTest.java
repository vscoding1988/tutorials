package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.entity.YugiohDataSet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YugiohMapperTest {

  private final YugiohMapper sut = new YugiohMapper(null);


  @ParameterizedTest
  @CsvSource(delimiter = ',', value = {
          "AST-070, AST",
          "PSV-E004, PSV-E",
          "PSV-EN004, PSV-EN",
          "RA04-089, RA04",
  })
  void testSetCodes(String code, String result) {
    // Given
    var set = new YugiohDataSet();
    set.setSetCode(code);

    // When/Then
    assertEquals(result, sut.getSetCode(set));
  }
}
