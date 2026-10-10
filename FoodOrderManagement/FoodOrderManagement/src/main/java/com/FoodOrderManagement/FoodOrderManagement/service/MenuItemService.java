package com.FoodOrderManagement.FoodOrderManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.MenuItem;
import com.FoodOrderManagement.FoodOrderManagement.entity.Restaurant;
import com.FoodOrderManagement.FoodOrderManagement.exception.MenuItemException;
import com.FoodOrderManagement.FoodOrderManagement.exception.MenuItemNotFoundException;
import com.FoodOrderManagement.FoodOrderManagement.repository.MenuItemRepository;
import com.FoodOrderManagement.FoodOrderManagement.repository.RestaurantRepository;

@Service
public class MenuItemService {

	@Autowired
	private MenuItemRepository menuItemRepo;

	@Autowired
	private RestaurantRepository restaurantRepo;
//1.add single menu item

	public ResponseStructure<MenuItem> saveMenuItem(MenuItem menuItem) {

		// Price cannot be negative
		if (menuItem.getPrice() < 0) {

			throw new MenuItemException("MenuItem Price Cannot Be Negative");
		}

		// MenuItem must contain Restaurant
		if (menuItem.getRestaurant() == null) {

			throw new MenuItemException("MenuItem Must Belong To A Restaurant");
		}

		// Get Restaurant ID
		Integer restaurantId = menuItem.getRestaurant().getId();

		// Check Restaurant exists
		Restaurant restaurant = restaurantRepo.findById(restaurantId)
				.orElseThrow(() -> new MenuItemException("Restaurant Not Found : " + restaurantId));

		// Set existing Restaurant
		menuItem.setRestaurant(restaurant);

		MenuItem savedMenuItem = menuItemRepo.save(menuItem);

		ResponseStructure<MenuItem> response = new ResponseStructure<>();

		response.setMessage("MenuItem Saved Successfully");
		response.setData(savedMenuItem);

		return response;
	}

	// 1
	public ResponseStructure<List<MenuItem>> saveMenuItems(List<MenuItem> menuItems) {

		for (MenuItem menuItem : menuItems) {

			// Price cannot be negative
			if (menuItem.getPrice() < 0) {

				throw new MenuItemException("MenuItem Price Cannot Be Negative");
			}

			// MenuItem must contain Restaurant
			if (menuItem.getRestaurant() == null) {

				throw new MenuItemException("MenuItem Must Belong To A Restaurant");
			}

			// Get Restaurant ID
			Integer restaurantId = menuItem.getRestaurant().getId();

			// Check Restaurant exists
			Restaurant restaurant = restaurantRepo.findById(restaurantId)
					.orElseThrow(() -> new MenuItemException("Restaurant Not Found : " + restaurantId));

			// Set existing Restaurant
			menuItem.setRestaurant(restaurant);
		}

		List<MenuItem> savedMenuItems = menuItemRepo.saveAll(menuItems);

		ResponseStructure<List<MenuItem>> response = new ResponseStructure<>();

		response.setMessage("MenuItems Saved Successfully");

		response.setData(savedMenuItems);

		return response;
	}

	// 9. from restaurent
	public ResponseStructure<List<MenuItem>> getMenuByRestaurant(Integer restaurantId) {

		List<MenuItem> menuItems = menuItemRepo.findByRestaurantId(restaurantId);

		if (menuItems.isEmpty()) {

			throw new MenuItemNotFoundException("No MenuItem Found For Restaurant : " + restaurantId);
		}

		ResponseStructure<List<MenuItem>> response = new ResponseStructure<>();

		response.setMessage("MenuItems Fetched Successfully");

		response.setData(menuItems);

		return response;
	}

	// 2.get all restorent
	public ResponseStructure<List<MenuItem>> getAllMenuItems() {

		List<MenuItem> menuItems = menuItemRepo.findAll();

		ResponseStructure<List<MenuItem>> response = new ResponseStructure<>();

		response.setMessage("All MenuItems Fetched Successfully");
		response.setData(menuItems);

		return response;
	}

	// 3.get ieam by the id
	public ResponseStructure<MenuItem> getMenuItemById(Integer id) {

		MenuItem menuItem = menuItemRepo.findById(id)
				.orElseThrow(() -> new MenuItemNotFoundException("MenuItem Not Found : " + id));

		ResponseStructure<MenuItem> response = new ResponseStructure<>();

		response.setMessage("MenuItem Fetched Successfully");
		response.setData(menuItem);

		return response;
	}

	// 4.update the restorent item based on price and availablility
	public ResponseStructure<MenuItem> updatePriceAndAvailability(Integer id, MenuItem menuItem) {

		MenuItem existingMenuItem = menuItemRepo.findById(id)
				.orElseThrow(() -> new MenuItemNotFoundException("MenuItem Not Found : " + id));

		// Price cannot be negative
		if (menuItem.getPrice() < 0) {

			throw new MenuItemException("MenuItem Price Cannot Be Negative");
		}

		// 4.Update only price
		existingMenuItem.setPrice(menuItem.getPrice());

		// Update only availability
		existingMenuItem.setAvailability(menuItem.getAvailability());

		MenuItem updatedMenuItem = menuItemRepo.save(existingMenuItem);

		ResponseStructure<MenuItem> response = new ResponseStructure<>();

		response.setMessage("MenuItem Price And Availability Updated Successfully");

		response.setData(updatedMenuItem);

		return response;
	}

	// 5.sort by price
	public ResponseStructure<List<MenuItem>> getMenuItemsByPriceAsc() {

		List<MenuItem> menuItems = menuItemRepo.findAllByOrderByPriceAsc();

		ResponseStructure<List<MenuItem>> response = new ResponseStructure<>();

		response.setMessage("MenuItems Sorted By Price Ascending");
		response.setData(menuItems);

		return response;
	}

	// 6. get item by name

	public ResponseStructure<MenuItem> getMenuItemByName(String itemName) {

		MenuItem menuItem = menuItemRepo.findByItemName(itemName);

		if (menuItem == null) {

			throw new MenuItemNotFoundException("MenuItem Not Found : " + itemName);
		}

		ResponseStructure<MenuItem> response = new ResponseStructure<>();

		response.setMessage("MenuItem Fetched Successfully");
		response.setData(menuItem);

		return response;
	}

	// 7. get all item in reastaurent

	public ResponseStructure<List<MenuItem>> getAllItemsInRestaurant(Integer restaurantId) {

		List<MenuItem> menuItems = menuItemRepo.findByRestaurantId(restaurantId);

		if (menuItems.isEmpty()) {

			throw new MenuItemNotFoundException("No MenuItem Found For Restaurant : " + restaurantId);
		}

		ResponseStructure<List<MenuItem>> response = new ResponseStructure<>();

		response.setMessage("All MenuItems In Restaurant Fetched Successfully");

		response.setData(menuItems);

		return response;
	}

}
