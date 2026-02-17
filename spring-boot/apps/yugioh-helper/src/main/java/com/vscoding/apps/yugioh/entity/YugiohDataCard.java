package com.vscoding.apps.yugioh.entity;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class YugiohDataCard {
  @Id
  private long id;

  private String name;

  private String type;

  private String humanReadableCardType;

  private String frameType;

  private String desc;

  private String race;

  @SerializedName("name_en")
  private String nameEn;

  @SerializedName("ygoprodeck_url")
  private String ygoprodeckUrl;

  @SerializedName("card_sets")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<YugiohDataSet> cardSets;

  @SerializedName("card_images")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<YugiohDataImage> cardImages;

  @SerializedName("card_prices")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<YugiohDataPrice> cardPrices;

  /**
   * Comma separated list of set names this card is part of, used for search purposes
   */
  private String setNames;
}
