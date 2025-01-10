package com.vscoding.urlshortener.boundary.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public class UrlDTO {
  private String shortUrl;
  private String targetUrl;
  private String description;
  private Date creationDate;
  private int calls = 0;
}
