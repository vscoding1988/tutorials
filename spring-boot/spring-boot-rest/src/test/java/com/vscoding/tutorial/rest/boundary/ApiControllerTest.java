package com.vscoding.tutorial.rest.boundary;

import com.vscoding.tutorial.rest.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

  @Autowired
  private MockMvc mvc;

  /**
   * This test handles an unknown api call, in this case we would expect a 404 response
   */
  @Test
  void callUnknownApi() throws Exception {
    //when
    mvc.perform(get("/api/not-found"))

            //Then
            .andExpect(status().isNotFound());
  }

  /**
   * This test handles "/api/products" call, we would except 10 Products as response.
   * To make sure the sorting is not off, we check the first and the last
   */
  @Test
  void getAllProducts() throws Exception {
    //when
    mvc.perform(get("/api/products"))

            //Then

            // Check for correct call
            .andExpect(status().isOk())

            // Check for correct count of products
            .andExpect(jsonPath("$", hasSize(10)))

            // check first product
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].name", is("Product1")))

            // check last product
            .andExpect(jsonPath("$[9].id", is(10)))
            .andExpect(jsonPath("$[9].name", is("Product10")));
  }

  /**
   * This test handles "/api/product/$ID" call, we would except one Product as response.
   */
  @Test
  void getProductById_success() throws Exception {
    //when
    mvc.perform(get("/api/product/3"))

            //Then

            // Check for correct call
            .andExpect(status().isOk())

            // check product
            .andExpect(jsonPath("$.id", is(3)))
            .andExpect(jsonPath("$.name", is("Product3")));
  }

  /**
   * This test handles "/api/product/$ID" call with not existing ID, we would except Not Found response.
   */
  @Test
  void getProductById_notFound() throws Exception {
    //when
    mvc.perform(get("/api/product/0"))

            //Then

            // Check for correct call
            .andExpect(status().isNotFound())

            // check product
            .andExpect(jsonPath("$.exception", is(ProductNotFoundException.class.getSimpleName())))
            .andExpect(jsonPath("$.message", is(new ProductNotFoundException(0).getMessage())));
  }

  /**
   * This test handles "/api/product/$ID" call with ID being a string, we would except Bad Request response.
   */
  @Test
  void getProductById_wrongIdFormat() throws Exception {
    //when
    mvc.perform(get("/api/product/errorId"))

            //Then

            // Check for correct call
            .andExpect(status().isBadRequest())

            // check product
            .andExpect(jsonPath("$.exception", is(MethodArgumentTypeMismatchException.class.getSimpleName())))
            .andExpect(jsonPath("$.message", containsString("errorId")));
  }
}
