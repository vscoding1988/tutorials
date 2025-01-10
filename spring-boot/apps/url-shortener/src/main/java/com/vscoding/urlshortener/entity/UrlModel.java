package com.vscoding.urlshortener.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.util.Date;

@Entity
@Data
@IdClass(UrlId.class)
@SuperBuilder
@NoArgsConstructor
public class UrlModel {
  /**
   * We persisting only a segment.
   */
  @Id
  private String shortUrl;
  @Id
  private String domain;

  private String targetUrl;
  @Column(length = 1023)
  private String description;

  private Date creationDate;
}
