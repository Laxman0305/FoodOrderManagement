package com.FoodOrderManagement.FoodOrderManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FoodOrderManagement.FoodOrderManagement.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

	List<Restaurant> findByLocation(String location);

	Restaurant findByName(String name);

	List<Restaurant> findByRatingGreaterThan(Double rating);

}
