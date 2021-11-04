package com.vscoding.urlshortner.control;

import com.vscoding.urlshortner.bean.UrlDTO;
import com.vscoding.urlshortner.entity.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {
  private final UrlRepository urlRepository;
  private final StatisticsRepository statisticsRepository;

  public UrlService(UrlRepository urlRepository, StatisticsRepository statisticsRepository) {
    this.urlRepository = urlRepository;
    this.statisticsRepository = statisticsRepository;
  }

  public List<UrlDTO> getUrls() {
    var result = new ArrayList<UrlDTO>();

    urlRepository.findAll().forEach(url -> {
      var dto = map(url);
      dto.setCalls(statisticsRepository.countAllByShortUrlIs(url.getShortUrl()));
      result.add(dto);
    });

    return result;
  }

  public Optional<UrlDTO> getUrl(UrlId urlId) {
    return urlRepository.findById(urlId).map(this::map);
  }

  private UrlDTO map(UrlModel url) {
    return UrlDTO.builder()
            .shortUrl(url.getShortUrl())
            .targetUrl(url.getTargetUrl())
            .description(url.getDescription())
            .build();
  }

  //TODO add referrer
  public void addRedirectCall(UrlId urlId) {
    var statisticsModel = new StatisticsModel(urlId, "unknown");
    statisticsRepository.save(statisticsModel);
  }
}
