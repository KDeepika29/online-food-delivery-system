package com.fooddelivery.restaurantservice.exceptions;

import java.lang.RuntimeException;


public class MenuCreationException extends RuntimeException {
    public MenuCreationException(String message) {
        super(message);
    }

    public MenuCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
