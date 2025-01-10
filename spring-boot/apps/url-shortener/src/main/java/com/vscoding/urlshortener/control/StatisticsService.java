package com.vscoding.urlshortener.control;

import com.vscoding.urlshortener.entity.StatisticsModel;
import com.vscoding.urlshortener.entity.StatisticsRepository;
import com.vscoding.urlshortener.entity.UrlId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {
  private final StatisticsRepository statisticsRepository;

  /**
   * Register a redirect in the statistics
   *
   * @param urlId   url id
   * @param request http request
   */
  public void registerRedirect(UrlId urlId, HttpServletRequest request) {
    var referer = request.getHeader(HttpHeaders.REFERER);
    var statisticsModel = new StatisticsModel(urlId, referer);

    statisticsRepository.save(statisticsModel);
  }
}
