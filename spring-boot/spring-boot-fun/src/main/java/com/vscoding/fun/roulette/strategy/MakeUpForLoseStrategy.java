package com.vscoding.fun.roulette.strategy;

import com.vscoding.fun.roulette.bean.RouletteState;

public class MakeUpForLoseStrategy implements BetStrategy {
  @Override
  public double betIfLoose(RouletteState state) {
    var bet = state.getCurrentBet() * 2;

    if (bet > state.getCurrentMoney()) {
      bet = state.getCurrentMoney();
    }

    return bet;
  }

  @Override
  public double betIfWin(RouletteState state) {
    return state.getConfig().getStartBet();
  }
}
