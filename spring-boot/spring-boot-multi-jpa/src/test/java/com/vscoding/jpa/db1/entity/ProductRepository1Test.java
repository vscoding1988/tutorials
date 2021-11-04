package com.vscoding.jpa.db1.entity;

import com.vscoding.jpa.db2.entity.ProductEntity2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductRepository1Test {
  @Autowired
  private ProductRepository1 repository;

  @Test
  void check_Repo() {
    assertEquals(0, repository.count());

    repository.save(new ProductEntity1("product1"));

    assertEquals(1, repository.count());

    repository.deleteAll();

    assertEquals(0, repository.count());
  }
}
