package com.vscoding.apps.yugioh.entity;

import com.google.gson.annotations.SerializedName;
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

  @SerializedName("image_url")
  private String imageUrl;

  @SerializedName("image_url_small")
  private String imageUrlSmall;

  @SerializedName("image_url_cropped")
  private String imageUrlCropped;
}
