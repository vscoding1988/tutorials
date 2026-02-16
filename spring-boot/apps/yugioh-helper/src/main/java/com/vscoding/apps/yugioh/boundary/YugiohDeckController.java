package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.DeckDTO;
import com.vscoding.apps.yugioh.boundary.bean.YugiohCreationRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationResponse;
import com.vscoding.apps.yugioh.control.YugiohDeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deck")
public class YugiohDeckController {
  private final YugiohDeckService mainService;

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public YugiohDeckCreationResponse createDeck(@RequestBody YugiohCreationRequest request) {
    return mainService.createDeck(request);
  }

  @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<DeckDTO> getDecks() {
    return mainService.getDecks();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DeckDTO getDeckById(String id) {
    return mainService.getDeck(id);
  }

  @DeleteMapping()
  public void deleteId(@RequestParam String id) {
    mainService.deleteDeck(id);
  }
}
