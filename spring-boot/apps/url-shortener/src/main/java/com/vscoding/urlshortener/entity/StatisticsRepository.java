package com.vscoding.urlshortener.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<StatisticsModel, Long> {
  int countAllByShortUrlIsAndDomainIs(String shortUrl, String domain);

  List<StatisticsModel> findAllByTimestampBetweenOrderByTimestamp(LocalDateTime from, LocalDateTime to);
}
