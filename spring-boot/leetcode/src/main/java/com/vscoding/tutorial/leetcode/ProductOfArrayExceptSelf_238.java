package com.vscoding.tutorial.leetcode;

public class ProductOfArrayExceptSelf_238 {

  /**
   * Run first from left to right and multiply, then from right to left.
   * <p>
   * initial array: [2, 3, 1, 4]
   * after first run: [1 (1), 2 (1x2), 6 (1x2x3), 6(1x2x3x1)]
   * after second run: [12 (1x1x4x3),8 (2x1x4),24 (6x4),6 (6)]
   *
   * @param nums
   * @return
   */
  public int[] productExceptSelf(int[] nums) {
    var result = new int[nums.length];

    var cumulativeProduct = 1;

    // Start with multiplying all form left to right
    for (int i = 0; i < nums.length; i++) {
      result[i] = cumulativeProduct;
      cumulativeProduct = cumulativeProduct * nums[i];
    }

    // Now multiply left to right value with right to left
    cumulativeProduct = 1;
    for (int j = nums.length - 1; j >= 0; j--) {
      result[j] = cumulativeProduct * result[j];
      cumulativeProduct = cumulativeProduct * nums[j];
    }

    return result;
  }
}
