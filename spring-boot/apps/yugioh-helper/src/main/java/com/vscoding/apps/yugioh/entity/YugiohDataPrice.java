package com.vscoding.apps.yugioh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class YugiohDataPrice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String cardmarket_price;
  private String tcgplayer_price;
  private String ebay_price;
  private String amazon_price;
  private String coolstuffinc_price;
}
