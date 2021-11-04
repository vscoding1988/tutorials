package com.vscoding.fun.roulette.bean;

import lombok.Data;

@Data
public class RouletteState {
  private RouletteInitConfig config;
  private double currentMoney;
  private double currentBet;

  private double maxMoney;
  private double minMoney;

  private int wins = 0;
  private int looses = 0;

  public RouletteState(RouletteInitConfig config) {
    this.currentBet = config.getStartBet();
    this.currentMoney = config.getStartMoney();
    this.maxMoney = config.getStartMoney();
    this.minMoney = config.getStartMoney();
    this.config = config;
  }

  public void loose() {
    currentMoney -= currentBet;
    if (currentMoney < minMoney) {
      minMoney = currentMoney;
    }
    looses++;
  }

  public void win() {
    currentMoney += currentBet;
    if (currentMoney > maxMoney) {
      maxMoney = currentMoney;
    }
    wins++;
  }
}
