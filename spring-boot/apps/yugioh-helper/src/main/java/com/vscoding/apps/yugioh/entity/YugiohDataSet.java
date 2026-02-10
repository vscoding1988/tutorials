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
public class YugiohDataSet {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String set_name;
  private String set_code;
  private String set_rarity;
  private String set_rarity_code;
  private String set_price;
}
