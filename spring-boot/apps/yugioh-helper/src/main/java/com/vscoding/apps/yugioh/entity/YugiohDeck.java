package com.vscoding.apps.yugioh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class YugiohDeck {
  @Id
  private String id;
  private String name;
  private String description;

  @ManyToMany
  private List<YugiohDataCard> mainDeck;
  @ManyToMany
  private List<YugiohDataCard> sideDeck;
  @ManyToMany
  private List<YugiohDataCard> extraDeck;
}
