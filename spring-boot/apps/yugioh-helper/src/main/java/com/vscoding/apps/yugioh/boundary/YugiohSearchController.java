package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class YugiohSearchController {

  @PostMapping
  public YugiohSearchResponse search(YugiohSearchRequest request) {
    return new YugiohSearchResponse(1, List.of());
  }
}
