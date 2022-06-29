package com.vscoding.tutorial.validation.boundary;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscoding.tutorial.validation.boundary.form.Company;
import com.vscoding.tutorial.validation.boundary.form.Form;
import com.vscoding.tutorial.validation.boundary.form.Product;
import com.vscoding.tutorial.validation.boundary.validation.CompanyIdValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class FormControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void validRequest() throws Exception {
    // Given
    var request = new Form();
    request.setCompany(new Company(CompanyIdValidator.ID_PREFIX + "1"));
    request.setProduct(new Product("1"));

    //When
    mvc.perform(post("/api/activate")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))

            //Then
            .andExpect(status().isOk());
  }

  @Test
  void failedRequest_companyNotFound() throws Exception {
    // Given
    var request = new Form();
    request.setCompany(new Company("1"));
    request.setProduct(new Product("1"));

    //When
    mvc.perform(post("/api/activate")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))

            //Then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("field", is("company.id")))
            .andExpect(jsonPath("message", is("The ID '1' could not be found.")));
  }

  @Test
  void failedRequest_productNotMatching() throws Exception {
    // Given
    var request = new Form();
    request.setCompany(new Company(CompanyIdValidator.ID_PREFIX + "1"));
    request.setProduct(new Product("2"));

    //When
    mvc.perform(post("/api/activate")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))

            //Then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("field", is("product.id")))
            .andExpect(jsonPath("message", is("The product id can not be activated by your company.")));
  }

  @Test
  void failedRequest_companyNull() throws Exception {
    // Given
    var request = new Form();
    request.setProduct(new Product("1"));

    //When
    mvc.perform(post("/api/activate")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))

            //Then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("field", is("company")))
            .andExpect(jsonPath("message", is("must not be null")));
  }

  @Test
  void failedRequest_companyIdNull() throws Exception {
    // Given
    var request = new Form();
    request.setCompany(new Company(null));
    request.setProduct(new Product("1"));

    //When
    mvc.perform(post("/api/activate")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))

            //Then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("field", is("company.id")))
            .andExpect(jsonPath("message", is("must not be blank")));
  }

  @Test
  void failedRequest_productNull() throws Exception {
    // Given
    var request = new Form();
    request.setCompany(new Company(CompanyIdValidator.ID_PREFIX + "1"));

    //When
    mvc.perform(post("/api/activate")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))

            //Then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("field", is("product")))
            .andExpect(jsonPath("message", is("must not be null")));
  }

  @Test
  void failedRequest_productIdNull() throws Exception {
    // Given
    var request = new Form();
    request.setCompany(new Company(CompanyIdValidator.ID_PREFIX + "1"));
    request.setProduct(new Product(null));

    //When
    mvc.perform(post("/api/activate")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))

            //Then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("field", is("product.id")))
            .andExpect(jsonPath("message", is("must not be blank")));
  }
}
