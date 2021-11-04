package com.vscoding.fun.roulette.strategy;

import com.vscoding.fun.roulette.bean.RouletteInitConfig;
import com.vscoding.fun.roulette.bean.RouletteState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MakeUpForLoseStrategyTest {
  private final BetStrategy sut = new MakeUpForLoseStrategy();

  @Test
  void betIfLoose_success() {
    var state = new RouletteState(new RouletteInitConfig(100, 5, 3));

    assertEquals(10D, sut.betIfLoose(state));
  }
}
