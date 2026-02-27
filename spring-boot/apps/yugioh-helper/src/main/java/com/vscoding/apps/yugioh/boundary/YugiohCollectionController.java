package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.*;
import com.vscoding.apps.yugioh.control.YugiohCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/collection")
public class YugiohCollectionController {
  private final YugiohCollectionService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CollectionDTO> getAllCollections() {
    return service.getAllCollections();
  }

  @GetMapping(value = "/sets", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CollectionSetDTO> getSetsInCollection(String collectionId) {
    return service.getSetsInCollection(collectionId);
  }

  @PostMapping(value = "/add-cards", produces = MediaType.APPLICATION_JSON_VALUE)
  public void addCards(@RequestBody List<AddCardDTO> cards) {
    service.addCardsToCollection(cards);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public YugiohCollectionSearchResponse search(@RequestBody YugiohCollectionSearchRequest request) {
    return service.search(request);
  }
}
