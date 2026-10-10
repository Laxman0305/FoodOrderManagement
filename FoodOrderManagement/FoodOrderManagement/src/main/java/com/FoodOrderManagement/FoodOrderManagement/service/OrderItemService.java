package com.FoodOrderManagement.FoodOrderManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrderManagement.FoodOrderManagement.repository.MenuItemRepository;
import com.FoodOrderManagement.FoodOrderManagement.repository.OrderItemRepository;
import com.FoodOrderManagement.FoodOrderManagement.repository.OrdersRepository;
import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.Availability;
import com.FoodOrderManagement.FoodOrderManagement.entity.MenuItem;
import com.FoodOrderManagement.FoodOrderManagement.entity.OrderItem;
import com.FoodOrderManagement.FoodOrderManagement.entity.Orders;
import com.FoodOrderManagement.FoodOrderManagement.exception.MenuItemNotFoundException;
import com.FoodOrderManagement.FoodOrderManagement.exception.OrderException;
import com.FoodOrderManagement.FoodOrderManagement.exception.OrderNotFoundException;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepo;

	@Autowired
	private OrdersRepository orderRepo;

	@Autowired
	private MenuItemRepository menuItemRepo;

	// 1. Add multiple items to existing order

	public ResponseStructure<List<OrderItem>> addMultipleItemsToOrder(Integer orderId, List<OrderItem> orderItems) {

		// Find existing Order
		Orders order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order Not Found : " + orderId));

		double totalAmount = order.getTotalAmount();

		// Check every OrderItem
		for (OrderItem orderItem : orderItems) {

			// Quantity validation
			if (orderItem.getQuantity() < 1) {

				throw new OrderException("Quantity Must Be At Least 1");
			}

			// MenuItem must be present
			if (orderItem.getMenuItem() == null) {

				throw new OrderException("OrderItem Must Contain MenuItem");
			}

			// Get MenuItem ID
			Integer menuItemId = orderItem.getMenuItem().getItemId();

			// Find MenuItem
			MenuItem menuItem = menuItemRepo.findById(menuItemId)
					.orElseThrow(() -> new MenuItemNotFoundException("MenuItem Not Found : " + menuItemId));

			// Check availability
			if (menuItem.getAvailability() != Availability.AVAILABLE) {

				throw new OrderException("MenuItem Is Currently Unavailable : " + menuItemId);
			}

			// Set actual MenuItem
			orderItem.setMenuItem(menuItem);

			// Calculate subtotal
			double subTotal = menuItem.getPrice() * orderItem.getQuantity();

			orderItem.setSubTotal(subTotal);

			// Set existing Order
			orderItem.setOrder(order);

			// Add to total
			totalAmount = totalAmount + subTotal;
		}

		// Save all OrderItems
		List<OrderItem> savedItems = orderItemRepo.saveAll(orderItems);

		// Update Order total
		order.setTotalAmount(totalAmount);

		orderRepo.save(order);

		ResponseStructure<List<OrderItem>> response = new ResponseStructure<>();

		response.setMessage("Multiple Items Added To Existing Order Successfully");

		response.setData(savedItems);

		return response;
	}

	// 2. Update OrderItem quantity

	public ResponseStructure<OrderItem> updateQuantity(Integer orderItemId, Integer quantity) {

		// Quantity validation
		if (quantity < 1) {
			throw new OrderException("Quantity Must Be At Least 1");
		}

		// Find OrderItem
		OrderItem orderItem = orderItemRepo.findById(orderItemId)
				.orElseThrow(() -> new OrderException("OrderItem Not Found : " + orderItemId));

		// Get MenuItem
		MenuItem menuItem = orderItem.getMenuItem();

		// Check MenuItem availability
		if (menuItem.getAvailability() != Availability.AVAILABLE) {

			throw new OrderException("MenuItem Is Currently Unavailable");
		}

		// Calculate new subtotal
		double subTotal = menuItem.getPrice() * quantity;

		// Update quantity
		orderItem.setQuantity(quantity);

		// Update subtotal
		orderItem.setSubTotal(subTotal);

		// Get existing Order
		Orders order = orderItem.getOrder();

		// Recalculate Order total
		double totalAmount = 0;

		for (OrderItem item : order.getOrderItems()) {

			if (item.getOrderItemId().equals(orderItemId)) {

				totalAmount = totalAmount + subTotal;

			} else {

				totalAmount = totalAmount + item.getSubTotal();
			}
		}

		// Set new order total
		order.setTotalAmount(totalAmount);

		// Save OrderItem
		OrderItem updatedItem = orderItemRepo.save(orderItem);

		// Save Order
		orderRepo.save(order);

		ResponseStructure<OrderItem> response = new ResponseStructure<>();

		response.setMessage("OrderItem Quantity Updated Successfully");

		response.setData(updatedItem);

		return response;
	}

	// Remove item from order before OUT_FOR_DELIVERY

	public ResponseStructure<Orders> removeItemFromOrder(Integer orderItemId) {

		// Find OrderItem
		OrderItem orderItem = orderItemRepo.findById(orderItemId)
				.orElseThrow(() -> new OrderException("OrderItem Not Found : " + orderItemId));

		// Get existing Order
		Orders order = orderItem.getOrder();

		// Check order status
		if (order.getStatus().equalsIgnoreCase("OUT_FOR_DELIVERY") || order.getStatus().equalsIgnoreCase("DELIVERED")) {

			throw new OrderException("Cannot Remove Item After Order Is Out For Delivery");
		}

		// Remove OrderItem from Order
		order.getOrderItems().remove(orderItem);

		// Recalculate total amount
		double totalAmount = 0;

		for (OrderItem item : order.getOrderItems()) {

			totalAmount = totalAmount + item.getSubTotal();
		}

		// Update order total
		order.setTotalAmount(totalAmount);

		// Update payment amount if payment exists
		if (order.getPayment() != null) {
			order.getPayment().setAmount(totalAmount);
		}

		// Save Order
		Orders updatedOrder = orderRepo.save(order);

		ResponseStructure<Orders> response = new ResponseStructure<>();

		response.setMessage("Item Removed From Order Successfully");

		response.setData(updatedOrder);

		return response;
	}

}
