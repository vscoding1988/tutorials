package com.vscoding.tutorial.leetcode;

/**
 * Given an array of integers citations where citations[i] is the number of citations a researcher received for their
 * ith paper, return the researcher's h-index.
 * <p>
 * According to the definition of h-index on Wikipedia:
 * The h-index is defined as the maximum value of h such that the given researcher has published at least h papers that
 * have each been cited at least h times.
 */
public class HIndex_274 {

  public int hIndex(int[] citations) {
    var lookup = createHIndexLookup(citations);

    var minCitation = 0;

    for (int i = lookup.length - 1; i >= 0; i--) {
      // Since we are going from top to bottom, we add the upper citations to the current one
      minCitation += lookup[i];

      if (minCitation >= i) {
        // We found the hIndex
        return i;
      }
    }

    return 0;
  }

  /**
   * This will generate a H-Index citation list which means
   *
   * @param citations unordered citation
   * @return H-Index citation list, i = count of citations, freq[2] = count of publication with exactly 2 citations
   */
  private int[] createHIndexLookup(int[] citations) {
    var n = citations.length;
    var freq = new int[n + 1];

    for (var i : citations) {
      if (i > n) {
        // [0,7] -> this would result in OutOfBound, so we increase the count of the highest h-index
        freq[n]++;
      } else {
        freq[i]++;
      }
    }

    return freq;
  }
}
