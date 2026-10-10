package com.FoodOrderManagement.FoodOrderManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FoodOrderManagement.FoodOrderManagement.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

	List<MenuItem> findByRestaurantId(Integer restaurantId);

	List<MenuItem> findAllByOrderByPriceAsc();

	MenuItem findByItemName(String itemName);

}
