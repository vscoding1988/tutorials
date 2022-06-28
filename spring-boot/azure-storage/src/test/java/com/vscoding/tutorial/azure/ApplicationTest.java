package com.vscoding.tutorial.azure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vscoding.tutorial.azure.control.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTest extends BaseIntegrationTest {

  @Test
  void contextLoads() {
    assertTrue(true);
  }
}
