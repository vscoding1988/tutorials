package com.vscoding.tutorial.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JumpGame_55Test {
  private JumpGame_55 sut = new JumpGame_55();

  @Test
  void test_canJump() {
    // Given
    var nums = new int[]{3,2,1,0,4};

    // When
    var result = sut.canJump(nums);

    // Then
    assertFalse(result);
  }
  @Test
  void test_canJump1() {
    // Given
    var nums = new int[]{2,0};

    // When
    var result = sut.canJump(nums);

    // Then
    assertTrue(result);
  }
  @Test
  void test_canJump2() {
    // Given
    var nums = new int[]{2,5,0,0};

    // When
    var result = sut.canJump(nums);

    // Then
    assertTrue(result);
  }
}
