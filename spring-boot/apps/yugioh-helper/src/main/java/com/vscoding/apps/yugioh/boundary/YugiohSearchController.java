package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchResponse;
import com.vscoding.apps.yugioh.control.YugiohSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
