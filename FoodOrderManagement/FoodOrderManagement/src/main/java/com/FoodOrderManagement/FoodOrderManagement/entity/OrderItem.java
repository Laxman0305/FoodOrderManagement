package com.FoodOrderManagement.FoodOrderManagement.entity;
import com.FoodOrderManagement.FoodOrderManagement.entity.Orders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.FoodOrderManagement.FoodOrderManagement.entity.MenuItem;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer orderItemId;

	    private Integer quantity;

	    private Double subTotal;

	    @ManyToOne
	    @JoinColumn(name = "order_id")
	    @JsonIgnore
	    private Orders order;

	    @ManyToOne
	    @JoinColumn(name = "menu_item_id")
	    private MenuItem menuItem;
	    
	    

}
