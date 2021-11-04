package com.vscoding.urlshortner.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class UrlDTO {
  private String shortUrl;
  private String targetUrl;
  private String description;
  private int calls = 0;
}
