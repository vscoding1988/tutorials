package com.vscoding.jpa.db2.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductCustomRepositoryTest {
  @Autowired
  private ProductCustomRepository sut;

  @Test
  void customQuery() {
    //Given
    var special = new ProductEntity2("special");
    sut.save(special);

    //When
    var result = sut.customQuery();

    //Then
    assertTrue(result.contains(special));
  }
}
