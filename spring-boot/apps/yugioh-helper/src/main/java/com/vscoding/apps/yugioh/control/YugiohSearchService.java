package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchResponse;
import com.vscoding.apps.yugioh.entity.YugiohDataCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YugiohSearchService {
  private final YugiohDataCardRepository repository;
  private final YugiohMapper mapper = new YugiohMapper();

  public YugiohSearchResponse search(YugiohSearchRequest request) {
    var pageable = Pageable.ofSize(25);

    var result = repository.findAllByNameContainingIgnoreCaseOrNameEnContainingIgnoreCase(request.query(), request.query(), pageable);
    var cards = result.get().map(mapper::mapLazy).toList();

    return new YugiohSearchResponse(result.getTotalElements(), cards);
  }
}
