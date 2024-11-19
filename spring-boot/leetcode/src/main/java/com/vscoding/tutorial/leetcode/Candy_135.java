package com.vscoding.tutorial.leetcode;

import java.util.Arrays;

/**
 * There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
 * <p>
 * You are giving candies to these children subjected to the following requirements:
 * <p>
 * - Each child must have at least one candy.
 * - Children with a higher rating get more candies than their neighbors.
 * <p>
 * Return the minimum number of candies you need to have to distribute the candies to the children.
 */
public class Candy_135 {


  public int candy(int[] ratings) {
    var candies = getCandyArrayBasedOnLeftChild(ratings);

    // We skip iterating the last element, so we need to add it here
    var sum = candies[ratings.length - 1];

    for (var i = ratings.length - 2; i >= 0; i--) {

      if (ratings[i] > ratings[i + 1]) {
        candies[i] = Math.max(candies[i], candies[i + 1] + 1);
      }

      sum += candies[i];
    }

    return sum;
  }

  /**
   * Will compare the child with his previous neighbor and if the child has a higher rating, the child will get one more
   * candy than his neighbor
   *
   * @param ratings of the children
   * @return array of candies only checking the left child
   */
  public int[] getCandyArrayBasedOnLeftChild(int[] ratings) {
    var candies = new int[ratings.length];
    // We don't need to fill the full array, just first entry is sufficient,
    // since all others are populated in the else clause
    candies[0] = 1;

    for (var i = 1; i < ratings.length; i++) {
      if (ratings[i] > ratings[i - 1]) {
        candies[i] = candies[i - 1] + 1;
      } else {
        candies[i] = 1;
      }
    }

    return candies;
  }
}
