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
public class YugiohDataSet {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @SerializedName("set_name")
  private String setName;

  @SerializedName("set_code")
  private String setCode;

  @SerializedName("set_rarity")
  private String setRarity;

  @SerializedName("set_rarity_code")
  private String setRarityCode;

  @SerializedName("set_price")
  private String setPrice;
}
