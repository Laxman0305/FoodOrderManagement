package com.FoodOrderManagement.FoodOrderManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.FoodOrderManagement.FoodOrderManagement.entity.Orders;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    private String paymentMethod;

    private String status;

    private Double amount;
    
    @OneToOne(mappedBy = "payment")
    @JsonIgnore
    private Orders order;
}