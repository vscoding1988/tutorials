package com.vscoding.tutorial.azure.exception;

public class BlobNotFoundException extends RuntimeException{

  public BlobNotFoundException(String fileName) {
    super("Blob with the name '"+fileName+"' not found");
  }
}
