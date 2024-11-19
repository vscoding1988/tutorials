package com.vscoding.tutorial.leetcode;

import java.util.*;

public class GetRandom_380 {
  private final Map<Integer, Integer> indexLookup = new HashMap<>();
  private final List<Integer> list = new ArrayList<>();
  private final Random rand = new Random();


  public boolean insert(int val) {
    if (indexLookup.containsKey(val)) {
      return false;
    }

    // Save the index of the new object, so that we can remove by index and not by value
    indexLookup.put(val, list.size());
    list.add(val);

    return true;
  }

  /**
   * We can't just delete the elements, since this would mess up the whole index. Instead, we override the value to be
   * deleted with the last value, and delete then the last value.
   *
   * @param val to delete
   * @return success
   */
  public boolean remove(int val) {
    if (!indexLookup.containsKey(val)) {
      return false;
    }

    var indexToRemove = indexLookup.get(val);

    // Save some time by not processing last index
    if (indexToRemove < list.size() - 1) {
      int lastElement = list.get(indexLookup.size() - 1);

      // Put last element on index of the element that has to be deleted
      list.set(indexToRemove, lastElement);
      indexLookup.put(lastElement, indexToRemove);
    }

    // Remove last element
    list.remove(list.size() - 1);
    indexLookup.remove(val);

    return true;
  }

  public int getRandom() {
    var randomIndex = rand.nextInt(list.size());
    return list.get(randomIndex);
  }
}
