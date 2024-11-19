package com.vscoding.tutorial.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestTimeToBuy2_122Test {
  private BestTimeToBuy2_122 sut = new BestTimeToBuy2_122();

  @Test
  void test_maxProfit() {
    // Given
    var prices = new int[]{7, 1, 5, 3, 6, 4};

    // When
    var result = sut.maxProfit(prices);

    // Then
    assertEquals(7, result);
  }
  @Test
  void test_maxProfit2() {
    // Given
    var prices = new int[]{1,2,3,4,5};

    // When
    var result = sut.maxProfit(prices);

    // Then
    assertEquals(4, result);
  }
}
