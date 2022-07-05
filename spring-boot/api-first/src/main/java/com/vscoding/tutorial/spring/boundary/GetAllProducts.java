package com.vscoding.tutorial.spring.boundary;

import com.vscoding.tutorial.spring.generated.api.GetAllProductsApi;
import com.vscoding.tutorial.spring.generated.model.Product;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAllProducts implements GetAllProductsApi {

  @Override
  public ResponseEntity<List<Product>> getAllProducts() {
    return GetAllProductsApi.super.getAllProducts();
  }
}
