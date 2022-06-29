package com.vscoding.tutorial.validation.boundary.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CompanyIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatedCompanyId {

  String message() default "The company Id could not be found.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
