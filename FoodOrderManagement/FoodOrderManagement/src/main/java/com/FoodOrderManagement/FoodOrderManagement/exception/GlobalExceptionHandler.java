

package com.FoodOrderManagement.FoodOrderManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// 1
	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public ResponseEntity<ResponseStructure<String>> handleCustomerAlreadyExists(CustomerAlreadyExistsException e) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setMessage(e.getMessage());
		response.setData("Customer already exists");

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	// 2

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleCustomerNotFoundException(CustomerNotFoundException e) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setMessage(e.getMessage());
		response.setData("Customer not found");

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// 3
	@ExceptionHandler(CustomerDeleteException.class)
	public ResponseEntity<ResponseStructure<String>> handleCustomerDeleteException(CustomerDeleteException e) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setMessage(e.getMessage());
		response.setData("Customer deletion failed");

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// 4
	@ExceptionHandler(MenuItemException.class)
	public ResponseEntity<ResponseStructure<String>> handleMenuItemException(MenuItemException e) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setMessage(e.getMessage());
		response.setData("MenuItem Operation Failed");

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// 5
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ResponseStructure<String>> handleOrderException(OrderException e) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setMessage(e.getMessage());
		response.setData("Order Creation Failed");

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// 6
	@ExceptionHandler(RestaurantNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleRestaurantNotFound(RestaurantNotFoundException e) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setMessage(e.getMessage());
		response.setData("Restaurant Not Found");

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// 7
	@ExceptionHandler(MenuItemNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleMenuItemNotFound(MenuItemNotFoundException e) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setMessage(e.getMessage());
		response.setData("MenuItem Not Found");

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// Order Not Found
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleOrderNotFound(OrderNotFoundException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setMessage(ex.getMessage());
		response.setData("Order Not Found");

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// Payment Not Found
	@ExceptionHandler(PaymentNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handlePaymentNotFound(PaymentNotFoundException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setMessage(ex.getMessage());
		response.setData("Payment Not Found");

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// Customer Exception
	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<ResponseStructure<String>> handleCustomerException(CustomerException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setMessage(ex.getMessage());

		response.setData("Customer Validation Failed");

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}