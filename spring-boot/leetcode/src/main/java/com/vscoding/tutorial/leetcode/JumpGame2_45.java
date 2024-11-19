package com.vscoding.tutorial.leetcode;

public class JumpGame2_45 {
  public int jump(int[] nums) {
    if (nums.length <= 1) {
      return 0;
    }

    var jumps = 0;
    var pointer = 0;

    while (pointer < nums.length - 1) {
      var jumpSize = nums[pointer];

      if (pointer + jumpSize < nums.length - 1) {
        // We are not facing the end yet
        pointer = getMaxReachIndex(nums, pointer + 1, pointer + jumpSize);
      } else {
        // We are done
        pointer += jumpSize;
      }

      jumps++;
    }

    return jumps;
  }

  public int getMaxReachIndex(int[] nums, int start, int end) {
    var max = -1;
    var index = -1;

    for (var counter = start; counter <= end; counter++) {
      // We need to add the index of the jump to the jump potential, to get the maximal potential
      // array = [2,2,2] potential array = [2,3,4]
      var newReachablePoint = nums[counter] + counter;

      if (newReachablePoint > max) {
        max = newReachablePoint;
        index = counter;
      }
    }

    return index;
  }
}
