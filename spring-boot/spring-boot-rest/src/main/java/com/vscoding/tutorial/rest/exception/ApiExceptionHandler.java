package com.vscoding.tutorial.rest.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
    var exceptionResponse = new ExceptionResponse(ex.getClass().getSimpleName(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handleProductNotFoundException(ProductNotFoundException ex) {
    var exceptionResponse = new ExceptionResponse(ex.getClass().getSimpleName(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Provide custom error for TypeMismatchException
   */
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    var exceptionResponse = new ExceptionResponse(ex.getClass().getSimpleName(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }
}
