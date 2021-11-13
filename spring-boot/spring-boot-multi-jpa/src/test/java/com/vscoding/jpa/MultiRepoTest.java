package com.vscoding.jpa;

import com.vscoding.jpa.db1.entity.ProductEntity1;
import com.vscoding.jpa.db1.entity.ProductRepository1;
import com.vscoding.jpa.db2.entity.ProductEntity2;
import com.vscoding.jpa.db2.entity.ProductRepository2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MultiRepoTest {
  @Autowired
  private ProductRepository1 repository1;
  @Autowired
  private ProductRepository2 repository2;


  @Test
  void check_multi_repo() {
    assertEquals(0, repository1.count());
    assertEquals(0, repository2.count());

    repository1.save(new ProductEntity1("product1"));

    assertEquals(1, repository1.count());
    assertEquals(0, repository2.count());

    repository2.save(new ProductEntity2("product2"));

    assertEquals(1, repository1.count());
    assertEquals(1, repository2.count());

    repository2.deleteAll();

    assertEquals(1, repository1.count());
    assertEquals(0, repository2.count());

    repository1.deleteAll();

    assertEquals(0, repository1.count());
    assertEquals(0, repository2.count());
  }

  @BeforeEach
  @AfterEach
  void setUp() {
    repository1.deleteAll();
    repository2.deleteAll();
  }
}
