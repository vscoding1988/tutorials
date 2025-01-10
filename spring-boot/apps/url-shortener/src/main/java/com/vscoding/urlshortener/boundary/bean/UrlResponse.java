package com.vscoding.urlshortener.boundary.bean;

import java.util.List;

public record UrlResponse(long count, List<UrlDTO> urls) {
}
