package com.FoodOrderManagement.FoodOrderManagement.exception;

public class OrderNotFoundException extends RuntimeException {
	public OrderNotFoundException(String message) {
		super(message);
	}

}
