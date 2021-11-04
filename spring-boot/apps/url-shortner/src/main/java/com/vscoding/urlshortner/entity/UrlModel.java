package com.vscoding.urlshortner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

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
}
