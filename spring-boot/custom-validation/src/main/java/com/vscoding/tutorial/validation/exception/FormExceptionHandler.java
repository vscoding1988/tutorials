package com.vscoding.tutorial.validation.exception;

import static java.util.Collections.singletonList;

import com.vscoding.tutorial.validation.boundary.validation.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *  Make sure validation errors are returned in json format
 */
@RestControllerAdvice
@Slf4j
public class FormExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          final MethodArgumentNotValidException ex,
          final HttpHeaders headers,
          final HttpStatus status,
          final WebRequest request) {

    var error = new ValidationError();
    final var fieldError = ex.getFieldError();

    if ((fieldError != null)) {
      error.setField(fieldError.getField());
      error.setMessage(fieldError.getDefaultMessage());
    }

    return ResponseEntity.badRequest().headers(jsonHeader()).body(error);
  }

  private HttpHeaders jsonHeader() {
    final var httpHeaders = new HttpHeaders();
    httpHeaders.put(HttpHeaders.CONTENT_TYPE, singletonList(MediaType.APPLICATION_JSON_VALUE));
    return httpHeaders;
  }
}
