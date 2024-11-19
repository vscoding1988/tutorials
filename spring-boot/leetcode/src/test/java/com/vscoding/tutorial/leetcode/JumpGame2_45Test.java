package com.vscoding.tutorial.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JumpGame2_45Test {
  private JumpGame2_45 sut = new JumpGame2_45();
  @Test
  void test_jump() {
    // Given
    var nums = new int[]{2,3,1,1,4};

    // When
    var result = sut.jump(nums);

    // Then
    assertEquals(2, result);
  }
  @Test
  void test_jump2() {
    // Given
    var nums = new int[]{10,9,8,7,6,5,4,3,2,1,1,0};

    // When
    var result = sut.jump(nums);

    // Then
    assertEquals(2, result);
  }
}
