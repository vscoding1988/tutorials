package com.vscoding.tutorial.leetcode;

public class BestTimeToBuy2_122 {
  public int maxProfit(int[] prices) {
    // select First as Candidate
    // Check if there is a next wich is lower, than change candidat
    // [2,1,3] buy Candidat [2,1] -> take 1
    // Check if there is a higher and select it as sell candidate
    // [2,1,3,4] sell Candidat [3,4] -> take 4
    // if getting lower value, create profit and set lower as buy candidat
    var pointer = 0;
    var buyCandidateVal = prices[0];
    var sellCandidateVal = prices[1];
    var profit = 0;
    var searchingForSell = false;

    while (pointer < prices.length) {
      var value = prices[pointer];

      if (!searchingForSell && buyCandidateVal >= value) {
        // the dip continues
        buyCandidateVal = value;
      } else {
        if (!searchingForSell) {
          searchingForSell = true;
          sellCandidateVal = -1;
        }


        if (value < sellCandidateVal) {
          // We found a dip we should sell to the price saved before the dip
          profit += (sellCandidateVal - buyCandidateVal);
          buyCandidateVal = value;
          searchingForSell = false;
        } else if (pointer == prices.length - 1) {
          // handle when highest price is in the end
          profit += (value - buyCandidateVal);
        } else {
          // We found a higher sell value
          sellCandidateVal = value;
        }
      }
      pointer++;
    }

    return profit;
  }
}
