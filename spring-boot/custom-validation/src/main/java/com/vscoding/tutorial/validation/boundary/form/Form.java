package com.vscoding.tutorial.validation.boundary.form;

import com.vscoding.tutorial.validation.boundary.validation.ValidatedProductId;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Bean for user request submission
 */
@Getter
@Setter
@ValidatedProductId
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Form {

  /**
   * User company data
   */
  @NotNull
  @Valid
  Company company;

  /**
   * Product request
   */
  @NotNull
  @Valid
  Product product;
}
