package com.FoodOrderManagement.FoodOrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.OrderItem;
import com.FoodOrderManagement.FoodOrderManagement.entity.Orders;
import com.FoodOrderManagement.FoodOrderManagement.service.OrderItemService;

@RestController
@RequestMapping("/item")
public class OrderItemController {
	@Autowired
	private OrderItemService orderItemService;

	// Add multiple items to existing order

	@PostMapping("/orders/{orderId}/multiple-items")
	public ResponseEntity<ResponseStructure<List<OrderItem>>> addMultipleItemsToOrder(@PathVariable Integer orderId,
			@RequestBody List<OrderItem> orderItems) {

		ResponseStructure<List<OrderItem>> response = orderItemService.addMultipleItemsToOrder(orderId, orderItems);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// 2. Update OrderItem quantity

	@PutMapping("/order-items/{orderItemId}/quantity/{quantity}")
	public ResponseEntity<ResponseStructure<OrderItem>> updateQuantity(@PathVariable Integer orderItemId,
			@PathVariable Integer quantity) {

		ResponseStructure<OrderItem> response = orderItemService.updateQuantity(orderItemId, quantity);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 3. Remove item from order before OUT_FOR_DELIVERY
	@DeleteMapping("/order-items/{orderItemId}")
	public ResponseEntity<ResponseStructure<Orders>> removeItemFromOrder(@PathVariable Integer orderItemId) {

		ResponseStructure<Orders> response = orderItemService.removeItemFromOrder(orderItemId);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
