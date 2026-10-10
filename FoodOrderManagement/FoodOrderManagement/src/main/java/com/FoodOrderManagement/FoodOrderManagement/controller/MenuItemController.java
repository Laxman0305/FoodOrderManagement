package com.FoodOrderManagement.FoodOrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.MenuItem;
import com.FoodOrderManagement.FoodOrderManagement.service.MenuItemService;

@RestController
@RequestMapping("/controller")
public class MenuItemController {

	@Autowired
	private MenuItemService menuItemService;

	// 1.Add single MenuItem to Restaurant
	@PostMapping("/menu-item")
	public ResponseEntity<ResponseStructure<MenuItem>> saveMenuItem(@RequestBody MenuItem menuItem) {

		ResponseStructure<MenuItem> response = menuItemService.saveMenuItem(menuItem);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// 1. Add multiple MenuItems to Restaurant
	@PostMapping("/menu-items/multiple")
	public ResponseEntity<ResponseStructure<List<MenuItem>>> saveMenuItems(@RequestBody List<MenuItem> menuItems) {

		ResponseStructure<List<MenuItem>> response = menuItemService.saveMenuItems(menuItems);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// 3.9 Get MenuItems from Restaurant
	@GetMapping("/restaurants/{restaurantId}/menu")
	public ResponseEntity<ResponseStructure<List<MenuItem>>> getMenuByRestaurant(@PathVariable Integer restaurantId) {

		ResponseStructure<List<MenuItem>> response = menuItemService.getMenuByRestaurant(restaurantId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get All MenuItems

	@GetMapping("/menu-items")
	public ResponseEntity<ResponseStructure<List<MenuItem>>> getAllMenuItems() {

		ResponseStructure<List<MenuItem>> response = menuItemService.getAllMenuItems();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 3.Get MenuItem By ID

	@GetMapping("/menu-items/{id}")
	public ResponseEntity<ResponseStructure<MenuItem>> getMenuItemById(@PathVariable Integer id) {

		ResponseStructure<MenuItem> response = menuItemService.getMenuItemById(id);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 4.Update Price And Availability

	@PutMapping("/menu-items/{id}")
	public ResponseEntity<ResponseStructure<MenuItem>> updatePriceAndAvailability(@PathVariable Integer id,
			@RequestBody MenuItem menuItem) {

		ResponseStructure<MenuItem> response = menuItemService.updatePriceAndAvailability(id, menuItem);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 5.sort by price
	@GetMapping("/menu-items/price/ascending")
	public ResponseEntity<ResponseStructure<List<MenuItem>>> getMenuItemsByPriceAsc() {

		ResponseStructure<List<MenuItem>> response = menuItemService.getMenuItemsByPriceAsc();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 6.Get MenuItem By Name

	@GetMapping("/menu-items/name/{itemName}")
	public ResponseEntity<ResponseStructure<MenuItem>> getMenuItemByName(@PathVariable String itemName) {

		ResponseStructure<MenuItem> response = menuItemService.getMenuItemByName(itemName);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 7. get all item in restorent
	@GetMapping("/restaurants/{restaurantId}/items")
	public ResponseEntity<ResponseStructure<List<MenuItem>>> getAllItemsInRestaurant(
			@PathVariable Integer restaurantId) {

		ResponseStructure<List<MenuItem>> response = menuItemService.getAllItemsInRestaurant(restaurantId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
