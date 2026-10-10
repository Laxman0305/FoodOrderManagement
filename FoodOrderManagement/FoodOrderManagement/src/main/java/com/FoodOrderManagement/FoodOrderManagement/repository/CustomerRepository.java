package com.FoodOrderManagement.FoodOrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FoodOrderManagement.FoodOrderManagement.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsByEmail(String email);

	boolean existsByContact(Long contact);

	Customer findByContact(Long contact);

	Customer findByName(String name);

}