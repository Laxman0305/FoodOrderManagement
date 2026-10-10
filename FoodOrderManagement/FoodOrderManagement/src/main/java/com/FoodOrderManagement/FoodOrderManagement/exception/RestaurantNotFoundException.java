package com.FoodOrderManagement.FoodOrderManagement.exception;

public class RestaurantNotFoundException extends RuntimeException{
	
	public RestaurantNotFoundException(String message) {
		super(message);
	}

}
