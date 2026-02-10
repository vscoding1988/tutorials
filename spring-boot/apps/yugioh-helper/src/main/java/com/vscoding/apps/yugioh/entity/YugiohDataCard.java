package com.vscoding.apps.yugioh.entity;

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

  private String name_en;

  private String ygoprodeck_url;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<YugiohDataSet> card_sets;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<YugiohDataImage> card_images;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<YugiohDataPrice> card_prices;
}
