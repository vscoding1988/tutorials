package com.vscoding.tutorial.rest.control;

import com.vscoding.tutorial.rest.bean.Product;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Dummy product service.
 */
@Service
public class ProductService {
  private final List<Product> products;

  public ProductService() {
    products = new ArrayList<>();
  }

  /**
   * Will generate ten products with ID 1 to 10
   */
  @PostConstruct
  private void initProducts(){
    for (int i = 1; i < 11; i++) {
      products.add(new Product(i, "Product" + i));
    }
  }

  public List<Product> getAllProducts() {
    return products;
  }

  /**
   * Try to find product for given ID
   *
   * @param id of product
   * @return optional for given id
   */
  public Optional<Product> getProductById(int id) {
    return products.stream().filter(product -> product.getId() == id).findAny();
  }
}
