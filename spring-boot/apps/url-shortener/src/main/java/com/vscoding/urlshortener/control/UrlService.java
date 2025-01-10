package com.vscoding.urlshortener.control;

import com.vscoding.urlshortener.boundary.bean.UrlCreationRequest;
import com.vscoding.urlshortener.boundary.bean.UrlDTO;
import com.vscoding.urlshortener.boundary.bean.UrlRequest;
import com.vscoding.urlshortener.boundary.bean.UrlResponse;
import com.vscoding.urlshortener.entity.*;
import com.vscoding.urlshortener.exception.DuplicateUrlException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {
  private final UrlRepository urlRepository;
  private final StatisticsRepository statisticsRepository;

  /**
   * Find Urls in the database based on the user request
   *
   * @param urlRequest user request
   * @return search result
   */
  public UrlResponse getUrls(UrlRequest urlRequest) {
    Page<UrlModel> result;
    var pageRequest = PageRequest.of(urlRequest.page(), urlRequest.size().getSize());

    if (urlRequest.term() == null || urlRequest.term().isEmpty()) {
      result = urlRepository.findAllByOrderByCreationDateDesc(pageRequest);
    } else {
      result = urlRepository.findAllByShortUrlContainingOrTargetUrlContainingOrderByCreationDateDesc(urlRequest.term(), urlRequest.term(), pageRequest);
    }

    var hits = result
            .map(this::map)
            .stream().toList();

    return new UrlResponse(result.getTotalElements(), hits);
  }

  public Optional<UrlDTO> getUrl(UrlId urlId) {
    return urlRepository.findById(urlId).map(this::map);
  }


  /**
   * Map database UrlModel to UrlDTO
   *
   * @param url UrlModel
   * @return UrlDTO
   */
  private UrlDTO map(UrlModel url) {
    var calls = statisticsRepository.countAllByShortUrlIsAndDomainIs(url.getShortUrl(), url.getDomain());

    return UrlDTO.builder()
            .shortUrl(url.getDomain() + "/" + url.getShortUrl())
            .targetUrl(url.getTargetUrl())
            .description(url.getDescription())
            .creationDate(url.getCreationDate())
            .calls(calls)
            .build();
  }

  /**
   * Create a new short url
   *
   * @param creationRequest user request
   * @return created short url, or throw DuplicateUrlException if already exists
   */
  public UrlDTO create(UrlCreationRequest creationRequest) {
    var urlId = new UrlId("r", creationRequest.shortUrl());  // TODO
    var alreadyExists = urlRepository.existsById(urlId);

    if (alreadyExists) {
      throw new DuplicateUrlException();
    }

    var urlModel = UrlModel.builder()
            .targetUrl(creationRequest.targetUrl())
            .shortUrl(creationRequest.shortUrl())
            .domain("r")  // TODO
            .description("") // TODO
            .creationDate(new Date())
            .build();

    urlRepository.save(urlModel);
    return map(urlModel);
  }
}
