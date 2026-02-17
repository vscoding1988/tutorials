package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.AddCardDTO;
import com.vscoding.apps.yugioh.control.YugiohCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/collection")
public class YugiohCollectionController {
  private final YugiohCollectionService service;

  @PostMapping(value = "/add-cards", produces = MediaType.APPLICATION_JSON_VALUE)
  public void addCards(List<AddCardDTO> cards) {
    service.addCardsToCollection(cards);
  }
}
