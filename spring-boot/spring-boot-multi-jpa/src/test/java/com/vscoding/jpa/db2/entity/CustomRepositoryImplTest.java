package com.vscoding.jpa.db2.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomRepositoryImplTest {
  @Autowired
  private ProductRepository2 sut;

  @Test
  void customQuery() {
    //Given
    sut.save(new ProductEntity2("special"));

    //When
    var result = sut.customQuery();

    //Then
    assertEquals(1, result.size());
    assertEquals("special", result.get(0).getName());

    sut.deleteAll();
  }

  @BeforeEach
  @AfterEach
  void setUp() {
    sut.deleteAll();
  }
}
