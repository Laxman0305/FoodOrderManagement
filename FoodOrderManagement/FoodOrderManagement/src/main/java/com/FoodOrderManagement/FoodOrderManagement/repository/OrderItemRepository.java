package com.FoodOrderManagement.FoodOrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FoodOrderManagement.FoodOrderManagement.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
