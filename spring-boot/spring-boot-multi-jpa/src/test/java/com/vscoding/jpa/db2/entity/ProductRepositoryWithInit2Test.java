package com.vscoding.jpa.db2.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("with-init")
@SpringBootTest
class ProductRepositoryWithInit2Test {
  @Autowired
  private ProductRepository2 repository;


  @Test
  void check_Repo_withInit() {
    assertEquals(1, repository.count());
  }
}
