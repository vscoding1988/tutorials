package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.DeckDTO;
import com.vscoding.apps.yugioh.boundary.bean.PDFCreationRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationResponse;
import com.vscoding.apps.yugioh.control.PDFBuilder;
import com.vscoding.apps.yugioh.control.YugiohImageService;
import com.vscoding.apps.yugioh.control.YugiohMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YugiohController {

  private final PDFBuilder pdfBuilder;
  private final YugiohImageService imageService;
  private final YugiohMainService mainService;

  @PostMapping("/create-pdf")
  public ResponseEntity<byte[]> createPDF(PDFCreationRequest request) {
    var outputStream = pdfBuilder.buildPDF(request);

    if (outputStream == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=proxy-cards.pdf")
            .body(outputStream.toByteArray());
  }

  @GetMapping("/image/small/{cardId}")
  public ResponseEntity<byte[]> getImage(@PathVariable long cardId) {
    try {
      var image = imageService.getImage(cardId);

      return ResponseEntity.ok()
              .contentType(MediaType.IMAGE_JPEG)
              .body(image);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/create-deck")
  public YugiohDeckCreationResponse createDeck(@RequestBody YugiohDeckCreationRequest request) {
    return mainService.createDeck(request);
  }

  @GetMapping("/get-decks")
  public List<DeckDTO> getDecks() {
    return mainService.getDecks();
  }

  @GetMapping("/get-deck")
  public DeckDTO getDeckById(String id) {
    return mainService.getDeck(id);
  }

  @GetMapping("/delete-deck")
  public void deleteId(@RequestParam String id) {
    mainService.deleteDeck(id);
  }
}
