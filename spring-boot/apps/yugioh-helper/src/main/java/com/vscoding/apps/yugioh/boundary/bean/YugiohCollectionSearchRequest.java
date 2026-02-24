package com.vscoding.apps.yugioh.boundary.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YugiohCollectionSearchRequest extends  YugiohSearchRequest {
  private String collection;
}
