package com.vscoding.apps.yugioh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class YugiohCardCollection {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  private List<YugiohCardCollectionWrapper> cards;
  private String name;
  private String description;
  private Date creationDate;
}
