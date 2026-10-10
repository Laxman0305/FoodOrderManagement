package com.FoodOrderManagement.FoodOrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.Restaurant;
import com.FoodOrderManagement.FoodOrderManagement.service.RestaurantService;

@RestController
@RequestMapping("/controller")
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;

	// 1.addRestaurent
	
	@PostMapping("/restaurants")
	public ResponseEntity<ResponseStructure<Restaurant>> saveRestaurant(@RequestBody Restaurant restaurant) {

		ResponseStructure<Restaurant> response = restaurantService.saveRestaurant(restaurant);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// multiple
	
	@PostMapping("/restaurants/multiple")
	public ResponseEntity<ResponseStructure<List<Restaurant>>> saveRestaurants(
			@RequestBody List<Restaurant> restaurants) {

		ResponseStructure<List<Restaurant>> response = restaurantService.saveRestaurants(restaurants);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// 2.Get All Restaurants

	@GetMapping("/restaurants")
	public ResponseEntity<ResponseStructure<List<Restaurant>>> getAllRestaurants() {

		ResponseStructure<List<Restaurant>> response = restaurantService.getAllRestaurants();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 3.Get Restaurant By ID

	@GetMapping("/restaurants/{id}")
	public ResponseEntity<ResponseStructure<Restaurant>> getRestaurantById(@PathVariable Integer id) {

		ResponseStructure<Restaurant> response = restaurantService.getRestaurantById(id);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 4.Update Restaurant

	@PutMapping("/restaurants/{id}")
	public ResponseEntity<ResponseStructure<Restaurant>> updateRestaurant(@PathVariable Integer id,
			@RequestBody Restaurant restaurant) {

		ResponseStructure<Restaurant> response = restaurantService.updateRestaurant(id, restaurant);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 5.Delete Restaurant

	@DeleteMapping("/restaurants/{id}")
	public ResponseEntity<ResponseStructure<Restaurant>> deleteRestaurant(@PathVariable Integer id) {

		ResponseStructure<Restaurant> response = restaurantService.deleteRestaurant(id);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 6.Get Restaurants By Location

	@GetMapping("/restaurants/location/{location}")
	public ResponseEntity<ResponseStructure<List<Restaurant>>> getRestaurantsByLocation(@PathVariable String location) {

		ResponseStructure<List<Restaurant>> response = restaurantService.getRestaurantsByLocation(location);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 7.Get Restaurant By Name

	@GetMapping("/restaurants/name/{name}")
	public ResponseEntity<ResponseStructure<Restaurant>> getRestaurantByName(@PathVariable String name) {

		ResponseStructure<Restaurant> response = restaurantService.getRestaurantByName(name);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 8. Get Restaurants By Rating Greater Than

	@GetMapping("/restaurants/rating/{rating}")
	public ResponseEntity<ResponseStructure<List<Restaurant>>> getRestaurantsByRating(@PathVariable Double rating) {

		ResponseStructure<List<Restaurant>> response = restaurantService.getRestaurantsByRating(rating);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
