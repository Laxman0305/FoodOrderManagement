package com.FoodOrderManagement.FoodOrderManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.MenuItem;
import com.FoodOrderManagement.FoodOrderManagement.entity.Restaurant;
import com.FoodOrderManagement.FoodOrderManagement.exception.MenuItemNotFoundException;
import com.FoodOrderManagement.FoodOrderManagement.exception.RestaurantNotFoundException;
import com.FoodOrderManagement.FoodOrderManagement.repository.MenuItemRepository;
import com.FoodOrderManagement.FoodOrderManagement.repository.RestaurantRepository;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepo;

//	//1.addRestaurent
	public ResponseStructure<Restaurant> saveRestaurant(Restaurant restaurant) {

		Restaurant savedRestaurant = restaurantRepo.save(restaurant);

		ResponseStructure<Restaurant> response = new ResponseStructure<>();

		response.setMessage("Restaurant Saved Successfully");
		response.setData(savedRestaurant);

		return response;
	}

	// add the multiple object to the table

	public ResponseStructure<List<Restaurant>> saveRestaurants(List<Restaurant> restaurants) {

		List<Restaurant> savedRestaurants = restaurantRepo.saveAll(restaurants);

		ResponseStructure<List<Restaurant>> response = new ResponseStructure<>();

		response.setMessage("Restaurants Saved Successfully");
		response.setData(savedRestaurants);

		return response;
	}

	// 2.get all the restaurent
	public ResponseStructure<List<Restaurant>> getAllRestaurants() {

		List<Restaurant> restaurants = restaurantRepo.findAll();

		ResponseStructure<List<Restaurant>> response = new ResponseStructure<>();

		response.setMessage("All Restaurants Fetched Successfully");
		response.setData(restaurants);

		return response;
	}

	// 3.get by id
	public ResponseStructure<Restaurant> getRestaurantById(Integer id) {

		Restaurant restaurant = restaurantRepo.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found : " + id));

		ResponseStructure<Restaurant> response = new ResponseStructure<>();

		response.setMessage("Restaurant Fetched Successfully");
		response.setData(restaurant);

		return response;
	}

	// 4.upadate restorent

	public ResponseStructure<Restaurant> updateRestaurant(Integer id, Restaurant restaurant) {

		Restaurant existingRestaurant = restaurantRepo.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found : " + id));

		existingRestaurant.setName(restaurant.getName());
		existingRestaurant.setLocation(restaurant.getLocation());
		existingRestaurant.setRating(restaurant.getRating());

		Restaurant updatedRestaurant = restaurantRepo.save(existingRestaurant);

		ResponseStructure<Restaurant> response = new ResponseStructure<>();

		response.setMessage("Restaurant Updated Successfully");
		response.setData(updatedRestaurant);

		return response;
	}

	// 5.delete restaurent
	public ResponseStructure<Restaurant> deleteRestaurant(Integer id) {

		Restaurant restaurant = restaurantRepo.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found : " + id));

		restaurantRepo.delete(restaurant);

		ResponseStructure<Restaurant> response = new ResponseStructure<>();

		response.setMessage("Restaurant Deleted Successfully");
		response.setData(restaurant);

		return response;
	}

	// 6.get restaurent ny location
	public ResponseStructure<List<Restaurant>> getRestaurantsByLocation(String location) {

		List<Restaurant> restaurants = restaurantRepo.findByLocation(location);

		if (restaurants.isEmpty()) {

			throw new RestaurantNotFoundException("Restaurant Not Found At Location : " + location);
		}

		ResponseStructure<List<Restaurant>> response = new ResponseStructure<>();

		response.setMessage("Restaurants Fetched Successfully");
		response.setData(restaurants);

		return response;
	}

	// 7.get reastaurent by name
	public ResponseStructure<Restaurant> getRestaurantByName(String name) {

		Restaurant restaurant = restaurantRepo.findByName(name);

		if (restaurant == null) {

			throw new RestaurantNotFoundException("Restaurant Not Found With Name : " + name);
		}

		ResponseStructure<Restaurant> response = new ResponseStructure<>();

		response.setMessage("Restaurant Fetched Successfully");
		response.setData(restaurant);

		return response;
	}

	// 8. get rating by grater than a vlaue
	public ResponseStructure<List<Restaurant>> getRestaurantsByRating(Double rating) {

		List<Restaurant> restaurants = restaurantRepo.findByRatingGreaterThan(rating);

		if (restaurants.isEmpty()) {

			throw new RestaurantNotFoundException("No Restaurant Found With Rating Greater Than : " + rating);
		}

		ResponseStructure<List<Restaurant>> response = new ResponseStructure<>();

		response.setMessage("Restaurants Fetched Successfully");

		response.setData(restaurants);

		return response;
	}

	// 9.get menu of the restaurent

	@Autowired
	private MenuItemRepository menuItemRepo;

	public ResponseStructure<List<MenuItem>> getMenuByRestaurant(Integer restaurantId) {

		List<MenuItem> menuItems = menuItemRepo.findByRestaurantId(restaurantId);

		if (menuItems.isEmpty()) {

			throw new MenuItemNotFoundException("No Menu Found For Restaurant : " + restaurantId);
		}

		ResponseStructure<List<MenuItem>> response = new ResponseStructure<>();

		response.setMessage("Restaurant Menu Fetched Successfully");

		response.setData(menuItems);

		return response;
	}

}
