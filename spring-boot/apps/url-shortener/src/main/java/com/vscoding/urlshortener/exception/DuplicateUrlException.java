package com.vscoding.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Short URL already exists")
public class DuplicateUrlException extends RuntimeException {
}
