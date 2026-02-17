package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.CardDTO;
import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchResponse;
import com.vscoding.apps.yugioh.entity.YugiohDataCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YugiohSearchService {
  private final YugiohDataCardRepository repository;
  private final YugiohMapper mapper = new YugiohMapper();

  public YugiohSearchResponse search(YugiohSearchRequest request) {
    var pageable = PageRequest.of(request.getPage(), request.getLimit());

    var result = repository.findAllByNameContainingIgnoreCaseOrNameEnContainingIgnoreCase(request.getQuery(), request.getQuery(), pageable);
    var cards = result.get().map(mapper::mapLazy).toList();

    return new YugiohSearchResponse(result.getTotalElements(), cards);
  }

  public CardDTO searchCardById(String id) {
    try {
      return repository.findById(Long.parseLong(id))
              .map(mapper::map)
              .orElse(null);
    } catch (Exception e) {
      return null;
    }
  }
}
