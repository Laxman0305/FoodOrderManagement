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
import com.FoodOrderManagement.FoodOrderManagement.entity.Customer;

import com.FoodOrderManagement.FoodOrderManagement.service.ControllerService;

@RestController
@RequestMapping("/controller")
public class CustomerController {

	@Autowired
	private ControllerService customerService;

	// 1
	@PostMapping("/customer")
	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(@RequestBody Customer customer) {

		ResponseStructure<Customer> response = customerService.saveCustomer(customer);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


	// 1.save multiple customer
	@PostMapping("/customers")
	public ResponseEntity<ResponseStructure<List<Customer>>> saveCustomers(@RequestBody List<Customer> customers) {

		ResponseStructure<List<Customer>> response = customerService.saveCustomers(customers);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// 2.getall customers
	@GetMapping("/customers")
	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomers() {

		ResponseStructure<List<Customer>> response = customerService.getAllCustomers();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 3.get By ID
	@GetMapping("/customers/{id}")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerById(@PathVariable Integer id) {

		ResponseStructure<Customer> response = customerService.getCustomerById(id);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 4.update the customer

	@PutMapping("/customers/{id}")
	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(@PathVariable Integer id,
			@RequestBody Customer customer) {

		ResponseStructure<Customer> response = customerService.updateCustomer(id, customer);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 5. Delete Customer

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<ResponseStructure<Customer>> deleteCustomer(@PathVariable Integer id) {

		ResponseStructure<Customer> response = customerService.deleteCustomer(id);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	

	// 6.find by contact number

	@GetMapping("/customers/contact/{contact}")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerByContact(@PathVariable Long contact) {

		ResponseStructure<Customer> response = customerService.getCustomerByContact(contact);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	

	// 7.get by name unique

	@GetMapping("/customers/name/{name}")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerByName(@PathVariable String name) {

		ResponseStructure<Customer> response = customerService.getCustomerByName(name);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
