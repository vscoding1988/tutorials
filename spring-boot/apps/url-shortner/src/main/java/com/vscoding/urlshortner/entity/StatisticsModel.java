package com.vscoding.urlshortner.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
  private Date timestamp;

  public StatisticsModel(UrlId urlId, String referrer) {
    this.shortUrl = urlId.getShortUrl();
    this.domain = urlId.getDomain();
    this.timestamp = new Date();
    this.referrer = referrer;
  }
}
