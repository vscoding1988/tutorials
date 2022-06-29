package com.vscoding.tutorial.validation.boundary;


import com.vscoding.tutorial.validation.boundary.form.Form;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class FormController {

  /**
   * Will return all existing products
   *
   * @return list of products
   */
  @PostMapping("activate")
  public ResponseEntity<Void> activateProduct(@Valid @RequestBody final Form form) {
    // Here we would place the product activation, but we are currently only interested in implementing the validator
    return ResponseEntity.ok().build();
  }
}
