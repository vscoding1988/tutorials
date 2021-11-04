package com.vscoding.fun.roulette;

import com.vscoding.fun.roulette.bean.RouletteInitConfig;
import com.vscoding.fun.roulette.bean.RouletteState;
import com.vscoding.fun.roulette.strategy.BetStrategy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RouletteGame {
  private final BetStrategy betStrategy;

  public void play(RouletteInitConfig config){
    var state = new RouletteState(config);

    for (int i = 0; i < config.getTries(); i++) {
      if(Math.random()>0.5){
        //win
        state.win();
        state.setCurrentBet(betStrategy.betIfWin(state));
      }else{
        //lose
        state.loose();
        state.setCurrentBet(betStrategy.betIfLoose(state));
      }
    }
  }
}
