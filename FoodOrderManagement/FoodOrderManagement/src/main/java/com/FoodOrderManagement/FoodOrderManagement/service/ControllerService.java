package com.FoodOrderManagement.FoodOrderManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrderManagement.FoodOrderManagement.DTO.ResponseStructure;
import com.FoodOrderManagement.FoodOrderManagement.entity.Customer;
import com.FoodOrderManagement.FoodOrderManagement.exception.CustomerAlreadyExistsException;
import com.FoodOrderManagement.FoodOrderManagement.exception.CustomerDeleteException;
import com.FoodOrderManagement.FoodOrderManagement.exception.CustomerException;
import com.FoodOrderManagement.FoodOrderManagement.exception.CustomerNotFoundException;
import com.FoodOrderManagement.FoodOrderManagement.repository.CustomerRepository;

@Service
public class ControllerService {
	@Autowired
	private CustomerRepository customerRepo;

	// 1.
	public ResponseStructure<Customer> saveCustomer(Customer customer) {

		// Email must be unique
		if (customerRepo.existsByEmail(customer.getEmail())) {

			throw new CustomerAlreadyExistsException("Email Already Exists : " + customer.getEmail());
		}

		// Contact must contain exactly 10 digits
		Long contact = customer.getContact();

		if (contact == null || contact < 1000000000L || contact > 9999999999L) {

			throw new CustomerException("Contact Number Must Contain Exactly 10 Digits");
		}

		// Contact must be unique
		if (customerRepo.existsByContact(contact)) {

			throw new CustomerAlreadyExistsException("Contact Number Already Exists : " + contact);
		}

		Customer savedCustomer = customerRepo.save(customer);

		ResponseStructure<Customer> response = new ResponseStructure<>();

		response.setMessage("Customer Saved Successfully");

		response.setData(savedCustomer);

		return response;
	}

	// 2,5
	public ResponseStructure<Customer> deleteCustomer(Integer id) {

		Customer customer = customerRepo.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found : " + id));

		if (customer.getOrders() == null || customer.getOrders().isEmpty()) {

			throw new CustomerDeleteException("Customer Cannot Be Deleted : No Completed Order Found");
		}

		boolean completedOrder = customer.getOrders().stream()
				.anyMatch(order -> order.getStatus().equalsIgnoreCase("Completed"));

		if (!completedOrder) {

			throw new CustomerDeleteException("Customer Cannot Be Deleted : Customer has no Completed Order");
		}

		customerRepo.delete(customer);

		ResponseStructure<Customer> response = new ResponseStructure<>();

		response.setMessage("Customer Deleted Successfully");
		response.setData(customer);

		return response;
	}

	// create api for the customer class

	// 1.
	public ResponseStructure<List<Customer>> saveCustomers(List<Customer> customers) {

		for (Customer customer : customers) {

			// Email must be unique
			if (customerRepo.existsByEmail(customer.getEmail())) {

				throw new CustomerAlreadyExistsException("Email Already Exists : " + customer.getEmail());
			}

			// Contact must be unique
			if (customerRepo.existsByContact(customer.getContact())) {

				throw new CustomerAlreadyExistsException("Contact Number Already Exists : " + customer.getContact());
			}
		}

		List<Customer> savedCustomers = customerRepo.saveAll(customers);

		ResponseStructure<List<Customer>> response = new ResponseStructure<>();

		response.setMessage("Customers Saved Successfully");
		response.setData(savedCustomers);

		return response;
	}

	// 2.getall customer
	public ResponseStructure<List<Customer>> getAllCustomers() {

		List<Customer> customers = customerRepo.findAll();

		ResponseStructure<List<Customer>> response = new ResponseStructure<>();

		response.setMessage("All Customers Fetched Successfully");
		response.setData(customers);

		return response;
	}

	// 3.get By Id
	public ResponseStructure<Customer> getCustomerById(Integer id) {

		Customer customer = customerRepo.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found : " + id));

		ResponseStructure<Customer> response = new ResponseStructure<>();

		response.setMessage("Customer Fetched Successfully");
		response.setData(customer);

		return response;
	}

	// 4.update the customer
	public ResponseStructure<Customer> updateCustomer(Integer id, Customer customer) {

		Customer existingCustomer = customerRepo.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found : " + id));

		// Check email uniqueness
		if (!existingCustomer.getEmail().equals(customer.getEmail())
				&& customerRepo.existsByEmail(customer.getEmail())) {

			throw new CustomerAlreadyExistsException("Email Already Exists : " + customer.getEmail());
		}

		// Check contact uniqueness
		if (!existingCustomer.getContact().equals(customer.getContact())
				&& customerRepo.existsByContact(customer.getContact())) {

			throw new CustomerAlreadyExistsException("Contact Number Already Exists : " + customer.getContact());
		}

		existingCustomer.setName(customer.getName());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setContact(customer.getContact());
		existingCustomer.setAddress(customer.getAddress());

		Customer updatedCustomer = customerRepo.save(existingCustomer);

		ResponseStructure<Customer> response = new ResponseStructure<>();

		response.setMessage("Customer Updated Successfully");
		response.setData(updatedCustomer);

		return response;
	}

	// 6.get by contact
	public ResponseStructure<Customer> getCustomerByContact(Long contact) {

		Customer customer = customerRepo.findByContact(contact);

		if (customer == null) {

			throw new CustomerNotFoundException("Customer Not Found With Contact : " + contact);
		}

		ResponseStructure<Customer> response = new ResponseStructure<>();

		response.setMessage("Customer Fetched Successfully");
		response.setData(customer);

		return response;
	}

	// 7.get by name unique

	public ResponseStructure<Customer> getCustomerByName(String name) {

		Customer customer = customerRepo.findByName(name);

		if (customer == null) {

			throw new CustomerNotFoundException("Customer Not Found With Name : " + name);
		}

		ResponseStructure<Customer> response = new ResponseStructure<>();

		response.setMessage("Customer Fetched Successfully");
		response.setData(customer);

		return response;
	}

}
