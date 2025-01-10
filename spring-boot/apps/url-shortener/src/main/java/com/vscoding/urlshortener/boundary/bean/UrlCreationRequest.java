package com.vscoding.urlshortener.boundary.bean;

public record UrlCreationRequest(String shortUrl, String targetUrl) {
}
