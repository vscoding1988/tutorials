package com.vscoding.tutorial.validation.boundary.validation;

import static java.lang.String.format;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Custom {@link ConstraintValidator} for {@link com.vscoding.tutorial.validation.boundary.form.Company}
 * id, which ensure that the id is valid. A valid id is defined as an id starting with "HID-"
 */
public class CompanyIdValidator implements ConstraintValidator<ValidatedCompanyId, String> {

  public static final String ID_PREFIX = "HID-";

  @Override
  public boolean isValid(String id, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();

    if (id == null) {
      // Even when @NotNull annotation throws an error, the validator is still executed, so we skip it
      return true;
    }

    if (!id.startsWith(ID_PREFIX)) {
      context
              .buildConstraintViolationWithTemplate(format("The ID '%s' could not be found.", id))
              .addConstraintViolation();

      return false;
    }

    return true;
  }
}
