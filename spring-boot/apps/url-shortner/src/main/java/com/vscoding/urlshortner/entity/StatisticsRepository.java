package com.vscoding.urlshortner.entity;

import org.springframework.data.repository.CrudRepository;

public interface StatisticsRepository extends CrudRepository<StatisticsModel,Long> {
  int countAllByShortUrlIs(String shortUrl);
}
