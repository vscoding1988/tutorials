package com.vscoding.tutorial.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Candy_135Test {
  private Candy_135 sut = new Candy_135();

  @Test
  void test1() {
    // Given
    var rating = new int[]{1, 0, 2};

    // When
    var result = sut.candy(rating);

    // Then
    assertEquals(5, result);
  }

  @Test
  void test2() {
    // Given
    var rating = new int[]{1, 2, 2};

    // When
    var result = sut.candy(rating);

    // Then
    assertEquals(4, result);
  }

  @Test
  void test3() {
    // Given
    var rating = new int[]{1, 2, 87, 87, 87, 2, 1};

    // When
    var result = sut.candy(rating);

    // Then
    assertEquals(13, result);
  }

}
