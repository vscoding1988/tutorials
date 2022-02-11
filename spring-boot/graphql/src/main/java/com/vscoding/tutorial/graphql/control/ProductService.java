package com.vscoding.tutorial.graphql.control;

import com.vscoding.tutorial.graphql.bean.Product;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class ProductService {

  private static final List<Integer> PRODUCT_KEY = Arrays.asList(220, 1000, 200);

  public Flux<Product> getProducts(int limit) {
    return Flux.fromIterable(PRODUCT_KEY)
            .take(limit)
            .map(this::getProduct);
  }

  private Product getProduct(int wait) {
    try {
      Thread.sleep(wait);
    } catch (Exception e) {
      log.error("Error executing sleep", e);
      Thread.currentThread().interrupt();
    }
    return new Product("number-" + wait, "name-" + wait);
  }
}
