package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.CardDTO;
import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchResponse;
import com.vscoding.apps.yugioh.control.YugiohSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class YugiohSearchController {
  private final YugiohSearchService service;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public YugiohSearchResponse search(YugiohSearchRequest request) {
    return service.search(request);
  }

  @GetMapping(value = "card", produces = MediaType.APPLICATION_JSON_VALUE)
  public CardDTO search(@RequestParam String id) {
    return service.searchCardById(id);
  }
}
