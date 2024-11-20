package com.fooddelivery.restaurantservice.to;

public class ResponseTO<T> {

    private String status;
    private String message;
    private T data;

    // Constructor
    public ResponseTO(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // Static helper method to create success responses
    public static <T> ResponseTO<T> createSuccessResponse(T data) {
        return new ResponseTO<>("SUCCESS", "Request processed successfully", data);
    }

    // Static helper method to create error responses
    public static <T> ResponseTO<T> createErrorResponse(String errorMessage) {
        return new ResponseTO<>("ERROR", errorMessage, null);
    }
}