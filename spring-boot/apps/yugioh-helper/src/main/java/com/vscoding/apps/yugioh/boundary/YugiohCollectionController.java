package com.vscoding.apps.yugioh.boundary;

import com.vscoding.apps.yugioh.boundary.bean.PDFCreationRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohCreationRequest;
import com.vscoding.apps.yugioh.control.PDFBuilder;
import com.vscoding.apps.yugioh.control.YugiohCollectionService;
import com.vscoding.apps.yugioh.control.YugiohImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/collection")
public class YugiohCollectionController {
  private YugiohCollectionService service;

  @PostMapping("/add-cards")
  public List<String> addCards(YugiohCreationRequest request) {
   return service.createCollection(request);
  }
}
