package com.vscoding.urlshortener.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlModel, UrlId> {
  Page<UrlModel> findAllByShortUrlContainingOrTargetUrlContainingOrderByCreationDateDesc(String shortUrl, String targetUrl, Pageable pageable);
  Page<UrlModel> findAllByOrderByCreationDateDesc(Pageable pageable);
}
