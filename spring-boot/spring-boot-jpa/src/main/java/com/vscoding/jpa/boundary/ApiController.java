package com.vscoding.jpa.boundary;

import com.vscoding.jpa.entity.ProductEntity;
import com.vscoding.jpa.entity.ProductRepository;
import com.vscoding.jpa.exception.ProductNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

  private final ProductRepository productRepository;

  public ApiController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  /**
   * Will return all existing products
   *
   * @return list of products
   */
  @GetMapping("products")
  public Iterable<ProductEntity> getAllProducts() {
    return productRepository.findAll();
  }

  /**
   * Will try to find a product for given ID. If not found ProductNotFoundException exception will be thrown
   *
   * @param id product id
   * @return product for given id
   */
  @GetMapping("product/{id}")
  public ProductEntity getProductById(@PathVariable int id) {
    var product = productRepository.findById(id);

    if (product.isEmpty()) {
      throw new ProductNotFoundException(id);
    }

    return product.get();
  }
}
