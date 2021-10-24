package com.vscoding.tutorial.rest.exception;

public class ExceptionResponse {
  private final String exception;
  private final String message;

  public ExceptionResponse(String exception, String message) {
    this.exception = exception;
    this.message = message;
  }

  public String getException() {
    return exception;
  }

  public String getMessage() {
    return message;
  }
}
