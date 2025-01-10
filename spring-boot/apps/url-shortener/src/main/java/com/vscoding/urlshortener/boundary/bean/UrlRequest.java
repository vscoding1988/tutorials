package com.vscoding.urlshortener.boundary.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record UrlRequest(
        String term,
        @Schema(example = "0", description = "Page number starting with a 0")
        int page,
        Size size) {


  @Getter
  @RequiredArgsConstructor
  @Schema(description = "Size of the page")
  public enum Size {
    // Not sure what naming to use here
    SIZE_10(10),
    SIZE_25(25),
    SIZE_50(50);

    private final int size;
  }
}
