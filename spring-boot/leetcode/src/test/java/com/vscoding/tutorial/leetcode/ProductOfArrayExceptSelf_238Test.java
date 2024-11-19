package com.vscoding.tutorial.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ProductOfArrayExceptSelf_238Test {
  private ProductOfArrayExceptSelf_238 sut = new ProductOfArrayExceptSelf_238();

  @Test
  void test_productExceptSelf1() {
    // Given
    var prices = new int[]{1, 2, 3, 4};

    // When
    var result = sut.productExceptSelf(prices);

    // Then
    assertArrayEquals(new int[]{24, 12, 8, 6}, result);
  }

  @Test
  void test_productExceptSelf2() {
    // Given
    var prices = new int[]{-1, 1, 0, -3, 3};

    // When
    var result = sut.productExceptSelf(prices);

    // Then
    assertArrayEquals(new int[]{0, 0, 9, 0, 0}, result);
  }

  @Test
  void test_productExceptSelf3() {
    // Given
    var prices = new int[]{2, 3, 1, 4};

    // When
    var result = sut.productExceptSelf(prices);

    // Then
    assertArrayEquals(new int[]{12, 8, 24, 6}, result);
  }
}
