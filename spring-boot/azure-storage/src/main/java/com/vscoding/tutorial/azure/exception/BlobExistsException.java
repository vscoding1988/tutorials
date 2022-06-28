package com.vscoding.tutorial.azure.exception;

public class BlobExistsException extends RuntimeException{

  public BlobExistsException(String fileName) {
    super("Blob with the name '"+fileName+"' exists already");
  }
}
