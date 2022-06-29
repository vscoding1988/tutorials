package com.vscoding.tutorial.validation.boundary.validation;

import com.vscoding.tutorial.validation.boundary.form.Form;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Custom {@link ConstraintValidator} for product id, but because we need access to the product ID
 * and company ID we have to validate the whole {@link Form}.
 */
public class ProductIdValidator implements ConstraintValidator<ValidatedProductId, Form> {

  @Override
  public boolean isValid(Form form, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();

    if(form.getProduct() == null || form.getProduct().getId() == null|| form.getCompany() == null || form.getCompany().getId() == null){ // NOSONAR false positive, product/company can be null
      // Even when @NotNull annotation throws an error, the validator is still executed, so we skip it
      return true;
    }

    var productId = form.getProduct().getId();
    var companyId = form.getCompany().getId();

    if (!productId.equals(companyId.replace(CompanyIdValidator.ID_PREFIX, ""))) {
      context
              .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
              .addPropertyNode("product.id")
              .addConstraintViolation();

      return false;
    }

    return true;
  }
}
