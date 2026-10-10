package com.FoodOrderManagement.FoodOrderManagement.entity;

import java.util.List;
import com.FoodOrderManagement.FoodOrderManagement.entity.MenuItem;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String location;

    private Double rating;
    
    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<MenuItem> menuItems;
}