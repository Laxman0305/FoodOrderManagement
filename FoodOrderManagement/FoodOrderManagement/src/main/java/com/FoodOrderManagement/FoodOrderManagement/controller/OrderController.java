package com.FoodOrderManagement.FoodOrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.OrderItem;
import com.FoodOrderManagement.FoodOrderManagement.entity.Orders;
import com.FoodOrderManagement.FoodOrderManagement.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	// Save multiple orders
	@PostMapping("/orders/multiple")
	public ResponseEntity<ResponseStructure<List<Orders>>> saveOrders(@RequestBody List<Orders> orders) {

		ResponseStructure<List<Orders>> response = orderService.saveOrders(orders);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// 2.
	@PostMapping("/orders/calculate-total")
	public ResponseEntity<ResponseStructure<Orders>> calculateOrderTotal(@RequestBody Orders order) {

		ResponseStructure<Orders> response = orderService.calculateOrderTotal(order);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// @PostMapping("/order-items/calculate-subtotal")
	public ResponseEntity<ResponseStructure<OrderItem>> calculateSubTotal(@RequestBody OrderItem orderItem) {

		ResponseStructure<OrderItem> response = orderService.calculateSubTotal(orderItem);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//
	@PutMapping("/orders/{orderId}/cancel")
	public ResponseEntity<ResponseStructure<Orders>> cancelOrder(@PathVariable Integer orderId) {

		ResponseStructure<Orders> response = orderService.cancelOrder(orderId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//

	@GetMapping("/orders/{orderId}/check-payment")
	public ResponseEntity<ResponseStructure<Orders>> checkPaymentAmount(@PathVariable Integer orderId) {

		ResponseStructure<Orders> response = orderService.checkPaymentAmount(orderId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 2. Get all orders

	@GetMapping("/orders")
	public ResponseEntity<ResponseStructure<List<Orders>>> getAllOrders() {

		ResponseStructure<List<Orders>> response = orderService.getAllOrders();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 3.Get all orders of a customer

	@GetMapping("/orders/customer/{customerId}")
	public ResponseEntity<ResponseStructure<List<Orders>>> getOrdersByCustomer(@PathVariable Integer customerId) {

		ResponseStructure<List<Orders>> response = orderService.getOrdersByCustomer(customerId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 4.Get Order By ID

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<ResponseStructure<Orders>> getOrderById(@PathVariable Integer orderId) {

		ResponseStructure<Orders> response = orderService.getOrderById(orderId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 5.Get Orders By Status

	@GetMapping("/orders/status/{status}")
	public ResponseEntity<ResponseStructure<List<Orders>>> getOrdersByStatus(@PathVariable String status) {

		ResponseStructure<List<Orders>> response = orderService.getOrdersByStatus(status);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 6.Get Orders By Date

	@GetMapping("/orders/date/{orderDate}")
	public ResponseEntity<ResponseStructure<List<Orders>>> getOrdersByDate(@PathVariable String orderDate) {

		ResponseStructure<List<Orders>> response = orderService.getOrdersByDate(orderDate);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 7.Get all orders placed in a restaurant

	@GetMapping("/orders/restaurant/{restaurantId}")
	public ResponseEntity<ResponseStructure<List<Orders>>> getOrdersByRestaurant(@PathVariable Integer restaurantId) {

		ResponseStructure<List<Orders>> response = orderService.getOrdersByRestaurant(restaurantId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
