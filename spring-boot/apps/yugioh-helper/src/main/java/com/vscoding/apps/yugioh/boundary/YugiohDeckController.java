package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.DeckDTO;
import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationResponse;
import com.vscoding.apps.yugioh.control.YugiohDeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deck")
public class YugiohDeckController {
  private final YugiohDeckService mainService;

  @PostMapping("/create")
  public YugiohDeckCreationResponse createDeck(@RequestBody YugiohDeckCreationRequest request) {
    return mainService.createDeck(request);
  }

  @GetMapping("/get-all")
  public List<DeckDTO> getDecks() {
    return mainService.getDecks();
  }

  @GetMapping()
  public DeckDTO getDeckById(String id) {
    return mainService.getDeck(id);
  }

  @DeleteMapping()
  public void deleteId(@RequestParam String id) {
    mainService.deleteDeck(id);
  }
}
