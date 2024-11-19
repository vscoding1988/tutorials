package com.vscoding.tutorial.leetcode;

public class JumpGame_55 {
  public boolean canJump(int[] nums) {
    var maxReachablePoint = 0;

    for (var i = 0; i < nums.length; i++) {
      if (i > maxReachablePoint) {
        // Since we are not able to reach this index, we cant reach the end
        return false;
      }

      var newReachablePoint = i + nums[i];
      maxReachablePoint = Math.max(maxReachablePoint, newReachablePoint);
    }

    return true;
  }
}
