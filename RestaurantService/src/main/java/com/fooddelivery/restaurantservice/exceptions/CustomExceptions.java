package com.fooddelivery.restaurantservice.exceptions;

import java.lang.RuntimeException;

import org.springframework.http.HttpStatus;

public class CustomExceptions extends RuntimeException {

    private HttpStatus httpStatus;

    public CustomExceptions(String message) {
        super(message);
    }

    public CustomExceptions(String message, Throwable cause) {
        super(message, cause);
    }

   public CustomExceptions(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
