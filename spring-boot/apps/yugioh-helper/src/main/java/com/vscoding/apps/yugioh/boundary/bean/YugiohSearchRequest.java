package com.vscoding.apps.yugioh.boundary.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YugiohSearchRequest {
  private String query;
  private int page = 0;
  private int limit = 20;
}
