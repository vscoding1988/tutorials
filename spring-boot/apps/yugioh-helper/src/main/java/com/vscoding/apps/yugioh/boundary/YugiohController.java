package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.PDFCreationRequest;
import com.vscoding.apps.yugioh.control.PDFBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YugiohController {

  private final PDFBuilder pdfBuilder;

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
}
