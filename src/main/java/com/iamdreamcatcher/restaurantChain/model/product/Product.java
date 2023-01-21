package com.iamdreamcatcher.restaurantChain.model.product;

import com.iamdreamcatcher.restaurantChain.model.ingredient.Ingredient;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Ingredient> ingredients = new ArrayList<>();

    public Product(String name, String description, Double price, Restaurant restaurant, List<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
        this.ingredients = ingredients;
    }
    //To do: ad photo;
}
