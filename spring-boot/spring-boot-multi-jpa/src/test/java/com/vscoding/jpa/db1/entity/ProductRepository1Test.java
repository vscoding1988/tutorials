package com.vscoding.jpa.db1.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductRepository1Test {
  @Autowired
  private ProductRepository1 sut;

  @Test
  void check_Repo() {
    assertEquals(0, sut.count());

    sut.save(new ProductEntity1("product1"));

    assertEquals(1, sut.count());

    sut.deleteAll();

    assertEquals(0, sut.count());
  }

  @BeforeEach
  @AfterEach
  void setUp() {
    sut.deleteAll();
  }
}
