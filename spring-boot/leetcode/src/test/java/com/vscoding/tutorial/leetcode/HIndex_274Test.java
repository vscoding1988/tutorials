package com.vscoding.tutorial.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HIndex_274Test {
  private HIndex_274 sut = new HIndex_274();

  @Test
  void test_hIndex1() {
    // Given
    var nums = new int[]{3,0,6,1,5};

    // When
    var result = sut.hIndex(nums);

    // Then
    assertEquals(3, result);
  }
  @Test
  void test_hIndex2() {
    // Given
    var nums = new int[]{1,3,1};

    // When
    var result = sut.hIndex(nums);

    // Then
    assertEquals(1, result);
  }
  @Test
  void test_hIndex3() {
    // Given
    var nums = new int[]{0,1};

    // When
    var result = sut.hIndex(nums);

    // Then
    assertEquals(1, result);
  }
}
