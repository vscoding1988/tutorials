package com.vscoding.jpa.db2.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductRepository2Test {
  @Autowired
  private ProductRepository2 repository;

  @Test
  void check_Repo() {
    assertEquals(0, repository.count());

    repository.save(new ProductEntity2("product2"));

    assertEquals(1, repository.count());

    repository.deleteAll();

    assertEquals(0, repository.count());
  }
}
