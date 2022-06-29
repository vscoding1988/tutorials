package com.vscoding.tutorial.validation.boundary.validation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationError {
  String field;
  String message;
}
