package com.FoodOrderManagement.FoodOrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.Payment;
import com.FoodOrderManagement.FoodOrderManagement.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	// 1.Get payment by payment method
	
	@GetMapping("/method/{paymentMethod}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByMethod(@PathVariable String paymentMethod) {

		ResponseStructure<List<Payment>> response = paymentService.getPaymentByMethod(paymentMethod);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 2.get payment by order
	
	@GetMapping("/order/{orderId}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentByOrder(@PathVariable Integer orderId) {

		ResponseStructure<Payment> response = paymentService.getPaymentByOrder(orderId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 3.get element by status
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(@PathVariable String status) {

		ResponseStructure<List<Payment>> response = paymentService.getPaymentByStatus(status);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 4.get payement by id
	
	@GetMapping("/{paymentId}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(@PathVariable Integer paymentId) {

		ResponseStructure<Payment> response = paymentService.getPaymentById(paymentId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 5.update payment by status
	
	@PutMapping("/{paymentId}/status/{status}")
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(@PathVariable Integer paymentId,
			@PathVariable String status) {

		ResponseStructure<Payment> response = paymentService.updatePaymentStatus(paymentId, status);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}