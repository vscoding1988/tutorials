package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.PDFCreationRequest;
import com.vscoding.apps.yugioh.control.PDFBuilder;
import com.vscoding.apps.yugioh.control.YugiohImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YugiohController {

  private final PDFBuilder pdfBuilder;
  private final YugiohImageService imageService;

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
}
