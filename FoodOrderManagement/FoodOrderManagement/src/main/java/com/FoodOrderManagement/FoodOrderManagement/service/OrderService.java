package com.FoodOrderManagement.FoodOrderManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.Availability;
import com.FoodOrderManagement.FoodOrderManagement.entity.Customer;
import com.FoodOrderManagement.FoodOrderManagement.entity.MenuItem;
import com.FoodOrderManagement.FoodOrderManagement.entity.OrderItem;
import com.FoodOrderManagement.FoodOrderManagement.entity.Orders;
import com.FoodOrderManagement.FoodOrderManagement.exception.OrderException;
import com.FoodOrderManagement.FoodOrderManagement.exception.OrderNotFoundException;
import com.FoodOrderManagement.FoodOrderManagement.repository.CustomerRepository;
import com.FoodOrderManagement.FoodOrderManagement.repository.MenuItemRepository;
import com.FoodOrderManagement.FoodOrderManagement.repository.OrdersRepository;

@Service

public class OrderService {

	@Autowired
	private OrdersRepository orderRepo;

	@Autowired
	private MenuItemRepository menuItemRepo;

	@Autowired
	private CustomerRepository customerRepo;

	// 1. Place multiple orders
	public ResponseStructure<List<Orders>> saveOrders(List<Orders> orders) {

		for (Orders order : orders) {

			// Order must contain Customer
			if (order.getCustomer() == null) {

				throw new OrderException("Order Must Contain Customer");
			}

			// Get Customer ID
			Integer customerId = order.getCustomer().getId();

			// Fetch existing Customer from database
			Customer customer = customerRepo.findById(customerId)
					.orElseThrow(() -> new OrderException("Customer Not Found : " + customerId));

			// Set actual Customer
			order.setCustomer(customer);

			// Order must contain Payment
			if (order.getPayment() == null) {

				throw new OrderException("Order Must Contain Payment");
			}

			// Order must contain at least one OrderItem
			if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {

				throw new OrderException("Order Must Contain At Least One OrderItem");
			}

			double totalAmount = 0;

			// Check every OrderItem
			for (OrderItem orderItem : order.getOrderItems()) {

				// OrderItem must contain MenuItem
				if (orderItem.getMenuItem() == null) {

					throw new OrderException("OrderItem Must Contain MenuItem");
				}

				// Quantity must be at least 1
				if (orderItem.getQuantity() < 1) {

					throw new OrderException("OrderItem Quantity Must Be At Least 1");
				}

				// Get MenuItem ID
				Integer menuItemId = orderItem.getMenuItem().getItemId();

				// Fetch actual MenuItem from database
				MenuItem menuItem = menuItemRepo.findById(menuItemId)
						.orElseThrow(() -> new OrderException("MenuItem Not Found : " + menuItemId));

				// Check MenuItem availability
				if (menuItem.getAvailability() != Availability.AVAILABLE) {

					throw new OrderException("MenuItem Is Currently Unavailable : " + menuItemId);
				}

				// Set actual MenuItem
				orderItem.setMenuItem(menuItem);

				// Calculate subtotal
				double subTotal = menuItem.getPrice() * orderItem.getQuantity();

				orderItem.setSubTotal(subTotal);

				// Set Order in OrderItem
				orderItem.setOrder(order);

				// Calculate total
				totalAmount = totalAmount + subTotal;
			}

			// Set calculated total
			order.setTotalAmount(totalAmount);

			// Payment amount must match total
			if (order.getPayment().getAmount() != totalAmount) {

				throw new OrderException("Payment Amount Must Be Same As Order Total Amount");
			}
		}

		// Save all Orders
		List<Orders> savedOrders = orderRepo.saveAll(orders);

		ResponseStructure<List<Orders>> response = new ResponseStructure<>();

		response.setMessage("Orders Saved Successfully");
		response.setData(savedOrders);

		return response;
	}

	// 2
	public ResponseStructure<Orders> saveOrders(Orders order) {

		// Order must contain Customer
		if (order.getCustomer() == null) {

			throw new OrderException("Order Must Contain Customer");
		}

		// Order must contain Payment
		if (order.getPayment() == null) {

			throw new OrderException("Order Must Contain Payment");
		}

		// Order must contain at least one OrderItem
		if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {

			throw new OrderException("Order Must Contain At Least One OrderItem");
		}

		double totalAmount = 0;

		// Check every OrderItem
		for (OrderItem orderItem : order.getOrderItems()) {

			// OrderItem must contain MenuItem
			if (orderItem.getMenuItem() == null) {

				throw new OrderException("OrderItem Must Contain MenuItem");
			}

			// MenuItem must be available
			if (orderItem.getMenuItem().getAvailability() != Availability.AVAILABLE) {

				throw new OrderException("MenuItem Is Currently Unavailable");
			}

			// Quantity must be greater than 0
			if (orderItem.getQuantity() <= 0) {

				throw new OrderException("OrderItem Quantity Must Be Greater Than 0");
			}

			// Calculate subtotal
			Double subTotal = orderItem.getQuantity() * orderItem.getMenuItem().getPrice();

			orderItem.setSubTotal(subTotal);

			// Set Order
			orderItem.setOrder(order);

			// Add subtotal to total
			totalAmount = totalAmount + subTotal;
		}

		// Set calculated total amount
		order.setTotalAmount(totalAmount);

		Orders savedOrder = orderRepo.save(order);

		ResponseStructure<Orders> response = new ResponseStructure<>();

		response.setMessage("Order Saved Successfully");
		response.setData(savedOrder);

		return response;
	}

	// // Calculate Order Total
	public ResponseStructure<Orders> calculateOrderTotal(Orders order) {

		// Order must contain at least one OrderItem
		if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {

			throw new OrderException("Order Must Contain At Least One OrderItem");
		}

		double totalAmount = 0.0;

		for (OrderItem orderItem : order.getOrderItems()) {

			// OrderItem must contain MenuItem
			if (orderItem.getMenuItem() == null) {

				throw new OrderException("OrderItem Must Contain MenuItem");
			}

			// Get MenuItem ID
			Integer menuItemId = orderItem.getMenuItem().getItemId();

			// Fetch actual MenuItem from database
			MenuItem menuItem = menuItemRepo.findById(menuItemId)
					.orElseThrow(() -> new OrderException("MenuItem Not Found : " + menuItemId));

			// Check MenuItem availability
			if (menuItem.getAvailability() != Availability.AVAILABLE) {

				throw new OrderException("MenuItem Is Currently Unavailable : " + menuItemId);
			}

			// Quantity must be at least 1
			if (orderItem.getQuantity() < 1) {

				throw new OrderException("OrderItem Quantity Must Be At Least 1");
			}

			// Set actual MenuItem
			orderItem.setMenuItem(menuItem);

			// Calculate SubTotal
			Double subTotal = menuItem.getPrice() * orderItem.getQuantity();

			orderItem.setSubTotal(subTotal);

			// Add SubTotal
			totalAmount += subTotal;
		}

		// Set calculated total
		order.setTotalAmount(totalAmount);

		ResponseStructure<Orders> response = new ResponseStructure<>();

		response.setMessage("Order Total Calculated Successfully");

		response.setData(order);

		return response;
	}

	// 2.Calculate SubTotal

	public ResponseStructure<OrderItem> calculateSubTotal(OrderItem orderItem) {

		// OrderItem must contain MenuItem
		if (orderItem.getMenuItem() == null) {

			throw new OrderException("OrderItem Must Contain MenuItem");
		}

		// MenuItem must be available
		if (orderItem.getMenuItem().getAvailability() != Availability.AVAILABLE) {

			throw new OrderException("MenuItem Is Currently Unavailable");
		}

		// Quantity must be at least 1
		if (orderItem.getQuantity() < 1) {

			throw new OrderException("OrderItem Quantity Must Be At Least 1");
		}

		// Calculate SubTotal = Price × Quantity
		Double subTotal = orderItem.getMenuItem().getPrice() * orderItem.getQuantity();

		orderItem.setSubTotal(subTotal);

		ResponseStructure<OrderItem> response = new ResponseStructure<>();

		response.setMessage("SubTotal Calculated Successfully");
		response.setData(orderItem);

		return response;
	}

	//
	// Cancel Order

	public ResponseStructure<Orders> cancelOrder(Integer orderId) {

		Orders order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderException("Order Not Found : " + orderId));

		// Order cannot be cancelled after preparing
		if (order.getStatus().equalsIgnoreCase("PREPARING")) {

			throw new OrderException("Order Cannot Be Cancelled After Preparing");
		}

		order.setStatus("CANCELLED");

		Orders cancelledOrder = orderRepo.save(order);

		ResponseStructure<Orders> response = new ResponseStructure<>();

		response.setMessage("Order Cancelled Successfully");
		response.setData(cancelledOrder);

		return response;
	}

	//
	// Check Order TotalAmount and Payment Amount

	public ResponseStructure<Orders> checkPaymentAmount(Integer orderId) {

		Orders order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderException("Order Not Found : " + orderId));

		// Check Payment exists
		if (order.getPayment() == null) {

			throw new OrderException("Order Must Contain Payment");
		}

		// Check Amount
		if (!order.getTotalAmount().equals(order.getPayment().getAmount())) {

			throw new OrderException("Order Total Amount Must Be Same As Payment Amount");
		}

		ResponseStructure<Orders> response = new ResponseStructure<>();

		response.setMessage("Order Amount And Payment Amount Matched Successfully");

		response.setData(order);

		return response;
	}

	// 2.get all oders

	public ResponseStructure<List<Orders>> getAllOrders() {

		List<Orders> orders = orderRepo.findAll();

		if (orders.isEmpty()) {
			throw new OrderNotFoundException("No Orders Found");
		}

		ResponseStructure<List<Orders>> response = new ResponseStructure<>();

		response.setMessage("All Orders Fetched Successfully");
		response.setData(orders);

		return response;
	}

	// 3. get all orders of a customer

	public ResponseStructure<List<Orders>> getOrdersByCustomer(Integer customerId) {

		List<Orders> orders = orderRepo.findByCustomerId(customerId);

		if (orders.isEmpty()) {
			throw new OrderNotFoundException("No Orders Found For Customer : " + customerId);
		}

		ResponseStructure<List<Orders>> response = new ResponseStructure<>();

		response.setMessage("Orders Fetched Successfully For Customer : " + customerId);

		response.setData(orders);

		return response;
	}

	// 4.get order by id
	public ResponseStructure<Orders> getOrderById(Integer orderId) {

		Orders order = orderRepo.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order Not Found : " + orderId));

		ResponseStructure<Orders> response = new ResponseStructure<>();

		response.setMessage("Order Fetched Successfully");
		response.setData(order);

		return response;
	}

	// 5.get order by status
	public ResponseStructure<List<Orders>> getOrdersByStatus(String status) {

		List<Orders> orders = orderRepo.findByStatus(status);

		if (orders.isEmpty()) {

			throw new OrderNotFoundException("No Orders Found With Status : " + status);
		}

		ResponseStructure<List<Orders>> response = new ResponseStructure<>();

		response.setMessage("Orders Fetched Successfully With Status : " + status);

		response.setData(orders);

		return response;
	}

	// 6.get order by date
	public ResponseStructure<List<Orders>> getOrdersByDate(String orderDate) {

		List<Orders> orders = orderRepo.findByOrderDate(orderDate);

		if (orders.isEmpty()) {

			throw new OrderNotFoundException("No Orders Found On Date : " + orderDate);
		}

		ResponseStructure<List<Orders>> response = new ResponseStructure<>();

		response.setMessage("Orders Fetched Successfully For Date : " + orderDate);

		response.setData(orders);

		return response;
	}

	// 7.get orders all orders placed in resataurent
	public ResponseStructure<List<Orders>> getOrdersByRestaurant(Integer restaurantId) {

		List<Orders> orders = orderRepo.findDistinctByOrderItemsMenuItemRestaurantId(restaurantId);

		if (orders.isEmpty()) {

			throw new OrderNotFoundException("No Orders Found For Restaurant : " + restaurantId);
		}

		ResponseStructure<List<Orders>> response = new ResponseStructure<>();

		response.setMessage("Orders Fetched Successfully For Restaurant : " + restaurantId);

		response.setData(orders);

		return response;
	}

}
