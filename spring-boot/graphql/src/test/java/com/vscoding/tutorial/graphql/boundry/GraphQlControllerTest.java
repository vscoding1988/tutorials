package com.vscoding.tutorial.graphql.boundry;

import static org.assertj.core.api.Assertions.assertThat;

import com.vscoding.tutorial.graphql.bean.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureWebGraphQlTester;
import org.springframework.graphql.test.tester.WebGraphQlTester;

@SpringBootTest
@AutoConfigureWebGraphQlTester
class GraphQlControllerTest {

  @Autowired
  private WebGraphQlTester graphQlTester;

  @Test
  @DisplayName("Find first 3 products")
  void getProducts() {

    this.graphQlTester.query("""
                      {
                        GetProducts(limit: 3) {
                          productNumber
                          productName
                        }
                      }
                    """)
            .execute()
            .path("GetProducts")
            .pathExists()
            .entityList(Product.class)
            .satisfies(items -> {
              assertThat(items).hasSize(3);
              var product = items.get(0);
              assertThat(product.getProductName()).isEqualTo("name-220");
              assertThat(product.getProductNumber()).isEqualTo("number-220");
            });
  }
}
