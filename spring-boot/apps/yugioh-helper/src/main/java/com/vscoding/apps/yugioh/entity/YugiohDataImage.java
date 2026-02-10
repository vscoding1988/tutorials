package com.vscoding.apps.yugioh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class YugiohDataImage {
  @Id
  private long id;
  private String image_url;
  private String image_url_small;
  private String image_url_cropped;
}
