package com.vscoding.apps.yugioh.entity;

import com.google.gson.annotations.SerializedName;
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

  @SerializedName("cardmarket_price")
  private String cardmarketPrice;

  @SerializedName("tcgplayer_price")
  private String tcgplayerPrice;

  @SerializedName("ebay_price")
  private String ebayPrice;

  @SerializedName("amazon_price")
  private String amazonPrice;

  @SerializedName("coolstuffinc_price")
  private String coolstuffincPrice;
}
