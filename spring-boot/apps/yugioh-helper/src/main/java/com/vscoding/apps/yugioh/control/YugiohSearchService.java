package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.CardDTO;
import com.vscoding.apps.yugioh.boundary.bean.SetDTO;
import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchResponse;
import com.vscoding.apps.yugioh.entity.YugiohDataCard;
import com.vscoding.apps.yugioh.entity.YugiohDataCardRepository;
import com.vscoding.apps.yugioh.entity.YugiohDataSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YugiohSearchService {
  private final YugiohDataCardRepository repository;
  private final YugiohDataSetRepository setRepository;
  private final YugiohMapper mapper;

  public YugiohSearchResponse search(YugiohSearchRequest request) {
    var pageable = PageRequest.of(request.getPage(), request.getLimit());

    Page<YugiohDataCard> result;

    if (request.getSet() == null || request.getSet().isEmpty()) {
      result = repository.findAllByNameContainingIgnoreCaseOrNameEnContainingIgnoreCase(request.getQuery(), request.getQuery(), pageable);
    } else {
      result = repository.findAllBySetNamesContainingIgnoreCase(request.getSet(), pageable);
    }

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

  public List<SetDTO> getAllSets() {
    return setRepository.findOnePerName().stream()
            .map(mapper::map)
            .toList();
  }
}
