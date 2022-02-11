package com.vscoding.tutorial.graphql.boundry;

import com.vscoding.tutorial.graphql.bean.Product;
import com.vscoding.tutorial.graphql.control.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
@AllArgsConstructor
public class GraphQlController {

  private ProductService service;

  @QueryMapping(name = "GetProducts")
  public Flux<Product> getProducts(@Argument int limit) {
    return service.getProducts(limit);
  }
}
