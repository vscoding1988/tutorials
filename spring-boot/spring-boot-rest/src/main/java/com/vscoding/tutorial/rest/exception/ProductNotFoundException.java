package com.vscoding.tutorial.rest.exception;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(int id) {
    super("Error getting product for id with value '" + id + "'.");
  }
}
