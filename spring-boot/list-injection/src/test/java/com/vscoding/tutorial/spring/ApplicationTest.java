package com.vscoding.tutorial.spring;

import static org.junit.jupiter.api.Assertions.*;

import com.vscoding.tutorial.spring.bean.ListItem;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTest {

  @Autowired
  private ListService listService;

  @Test
  void contextUp() {
    assertTrue(true);
  }

  @Test
  void test_autowire() {
    // When
    var listItems = listService.getListItems();

    // Then
    assertEquals(6, listItems.size());
  }
}
