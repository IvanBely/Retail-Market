package com.example.data_service.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
