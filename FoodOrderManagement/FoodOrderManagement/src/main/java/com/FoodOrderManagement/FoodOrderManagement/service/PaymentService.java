package com.FoodOrderManagement.FoodOrderManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.Payment;
import com.FoodOrderManagement.FoodOrderManagement.exception.PaymentNotFoundException;
import com.FoodOrderManagement.FoodOrderManagement.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepo;

	// 1. Get payments by payment method
	public ResponseStructure<List<Payment>> getPaymentByMethod(String paymentMethod) {

		List<Payment> payments = paymentRepo.findByPaymentMethod(paymentMethod);

		if (payments.isEmpty()) {
			throw new PaymentNotFoundException("No Payment Found With Method : " + paymentMethod);
		}

		ResponseStructure<List<Payment>> response = new ResponseStructure<>();

		response.setMessage("Payments Fetched Successfully");

		response.setData(payments);

		return response;
	}

	// 2.get payment by order
	public ResponseStructure<Payment> getPaymentByOrder(Integer orderId) {

		Payment payment = paymentRepo.findByOrderOrderId(orderId);

		if (payment == null) {

			throw new PaymentNotFoundException("Payment Not Found For Order : " + orderId);
		}

		ResponseStructure<Payment> response = new ResponseStructure<>();

		response.setMessage("Payment Fetched Successfully");

		response.setData(payment);

		return response;
	}

	// 3.get element by status
	public ResponseStructure<List<Payment>> getPaymentByStatus(String status) {

		List<Payment> payments = paymentRepo.findByStatus(status);

		if (payments.isEmpty()) {

			throw new PaymentNotFoundException("No Payment Found With Status : " + status);
		}

		ResponseStructure<List<Payment>> response = new ResponseStructure<>();

		response.setMessage("Payments Fetched Successfully");

		response.setData(payments);

		return response;
	}

	// 4.get element by id
	public ResponseStructure<Payment> getPaymentById(Integer paymentId) {

		Payment payment = paymentRepo.findById(paymentId)
				.orElseThrow(() -> new PaymentNotFoundException("Payment Not Found : " + paymentId));

		ResponseStructure<Payment> response = new ResponseStructure<>();

		response.setMessage("Payment Fetched Successfully");

		response.setData(payment);

		return response;
	}

	// 5.update element by status
	public ResponseStructure<Payment> updatePaymentStatus(Integer paymentId, String status) {

		// Find payment
		Payment payment = paymentRepo.findById(paymentId)
				.orElseThrow(() -> new PaymentNotFoundException("Payment Not Found : " + paymentId));

		// Update status
		payment.setStatus(status);

		// Save payment
		Payment updatedPayment = paymentRepo.save(payment);

		ResponseStructure<Payment> response = new ResponseStructure<>();

		response.setMessage("Payment Status Updated Successfully");

		response.setData(updatedPayment);

		return response;
	}
}
