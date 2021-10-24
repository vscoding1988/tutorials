package com.vscoding.tutorial.rest.boundry;

import com.vscoding.tutorial.rest.bean.Product;
import com.vscoding.tutorial.rest.control.ProductService;
import com.vscoding.tutorial.rest.exception.ProductNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {

  private final ProductService productService;

  public ApiController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * Will return all existing products
   * @return list of products
   */
  @GetMapping("products")
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  /**
   * Will try to find a product for given ID. If not found ProductNotFoundException exception will be thrown
   *
   * @param id product id
   * @return product for given id
   */
  @GetMapping("product/{id}")
  public Product getProductById(@PathVariable int id) {
    var product = productService.getProductById(id);

    if (product.isEmpty()) {
      throw new ProductNotFoundException(id);
    }

    return product.get();
  }
}
