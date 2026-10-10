package com.FoodOrderManagement.FoodOrderManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FoodOrderManagement.FoodOrderManagement.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	List<Payment> findByPaymentMethod(String paymentMethod);

	Payment findByOrderOrderId(Integer orderId);

	List<Payment> findByStatus(String status);

}
