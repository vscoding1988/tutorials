package com.vscoding.urlshortener.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class StatisticsModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String shortUrl;
  private String domain;
  private String referrer;
  private LocalDateTime timestamp;
  private int count;

  public StatisticsModel(UrlId urlId, String referrer) {
    this.shortUrl = urlId.getShortUrl();
    this.domain = urlId.getDomain();
    this.timestamp = LocalDateTime.now();
    this.referrer = referrer;
    this.count = 1;
  }
}
