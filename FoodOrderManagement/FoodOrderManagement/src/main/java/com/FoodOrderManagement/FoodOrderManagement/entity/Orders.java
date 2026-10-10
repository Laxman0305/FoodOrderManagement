	package com.FoodOrderManagement.FoodOrderManagement.entity;
	
	import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.FoodOrderManagement.FoodOrderManagement.entity.Customer;
import com.FoodOrderManagement.FoodOrderManagement.entity.Payment;
import com.FoodOrderManagement.FoodOrderManagement.entity.OrderItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
	
	@Entity
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public class Orders {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer orderId;
	
	    private String orderDate;
	
	    private String status;
	
	    private Double totalAmount;
	
	    @ManyToOne
	    @JoinColumn(name = "customer_id")
	    private Customer customer;
	    
	  
	    
	    @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "payment_id")
	    private Payment payment;
	    
	    @OneToMany(
	            mappedBy = "order",
	            cascade = CascadeType.ALL,
	            orphanRemoval = true
	    )	    
	    private List<OrderItem> orderItems;
	}