package com.FoodOrderManagement.FoodOrderManagement.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {

        super(message);
    }
}