package com.vscoding.tutorial.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GasStation_134Test {
  private GasStation_134 sut = new GasStation_134();

  @Test
  void test1() {
    // Given
    var gas = new int[]{1,2,3,4,5};
    var cost = new int[]{3,4,5,1,2};

    // When
    var result = sut.canCompleteCircuit(gas, cost);

    // Then
    assertEquals(3, result);
  }
  @Test
  void test2() {
    // Given
    var gas = new int[]{2,3,4};
    var cost = new int[]{3,4,3};

    // When
    var result = sut.canCompleteCircuit(gas, cost);

    // Then
    assertEquals(-1, result);
  }
  @Test
  void test3() {
    // Given
    var gas = new int[]{5,1,2,3,4};
    var cost = new int[]{4,4,1,5,1};

    // When
    var result = sut.canCompleteCircuit(gas, cost);

    // Then
    assertEquals(4, result);
  }
}
