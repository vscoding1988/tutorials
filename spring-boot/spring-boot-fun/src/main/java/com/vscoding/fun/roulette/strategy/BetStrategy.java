package com.vscoding.fun.roulette.strategy;

import com.vscoding.fun.roulette.bean.RouletteState;

public interface BetStrategy {
  double betIfLoose(RouletteState state);
  double betIfWin(RouletteState state);
}
