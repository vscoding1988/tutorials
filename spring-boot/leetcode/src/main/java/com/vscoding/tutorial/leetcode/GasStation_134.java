package com.vscoding.tutorial.leetcode;

/**
 * There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].
 * <p>
 * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station. You begin the journey with an empty tank at one of the gas stations.
 * <p>
 * Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1. If there exists a solution, it is guaranteed to be unique
 */
public class GasStation_134 {
  /**
   * The most important part about the task, is that if there is a solution, there is only one.
   *
   * @param gas  gas[i] represents the amount of gas available at the i-th gas
   * @param cost cost[i] represents the amount of gas needed to travel from the i-th gas station to the next one
   * @return start point
   */
  public int canCompleteCircuit(int[] gas, int[] cost) {
    var start = 0;
    var tank = 0;

    // This is a counter, if at the end it is negative, there is no solution, since we need to spend more gas, than we can buy
    var totalBalance = 0;

    for (int i = 0; i < gas.length; i++) {
      var balance = gas[i] - cost[i];

      tank += balance;
      totalBalance += balance;

      if (tank < 0) {
        start = i + 1;
        tank = 0;
      }
    }

    if (start == gas.length + 1 || totalBalance < 0) {
      return -1;
    }

    return start;
  }
}
