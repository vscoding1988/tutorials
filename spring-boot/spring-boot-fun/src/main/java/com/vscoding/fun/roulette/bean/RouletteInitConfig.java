package com.vscoding.fun.roulette.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouletteInitConfig {
  private final int startMoney;
  private final int startBet;
  private final int tries;

}
