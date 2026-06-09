package com.vscoding.tutorial.spring.control;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SharePointRunner {
  private final SharePointCountService countService;
  private final SharePointItemsService itemsService;

  @PostConstruct
  public void run() {
    var i = countService.countItems();

    log.info("Count: {}", i);

    var items = itemsService.getItems();

    log.info("Items: {}", items.size());
  }
}
