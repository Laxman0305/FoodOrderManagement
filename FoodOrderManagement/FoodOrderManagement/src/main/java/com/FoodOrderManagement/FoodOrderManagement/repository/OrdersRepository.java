package com.FoodOrderManagement.FoodOrderManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FoodOrderManagement.FoodOrderManagement.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	List<Orders> findByCustomerId(Integer customerId);

	List<Orders> findByStatus(String status);

	List<Orders> findByOrderDate(String orderDate);

	List<Orders> findDistinctByOrderItemsMenuItemRestaurantId(Integer restaurantId);

}
