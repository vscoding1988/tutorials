package com.vscoding.apps.yugioh.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YugiohDataResponse {
  private List<YugiohDataCard> data;
}
