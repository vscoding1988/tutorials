package com.vscoding.apps.yugioh.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class YugiohCardCollectionWrapper {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @ManyToOne
  private YugiohDataSet set;

  @ManyToOne
  private YugiohDataCard card;

  private int count = 0;
}
