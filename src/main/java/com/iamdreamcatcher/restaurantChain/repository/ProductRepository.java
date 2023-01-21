package com.iamdreamcatcher.restaurantChain.repository;

import com.iamdreamcatcher.restaurantChain.model.ingredient.Ingredient;
import com.iamdreamcatcher.restaurantChain.model.product.Product;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByRestaurant(Restaurant restaurant);
    List<Product> findProductsByIngredientsContaining(Ingredient ingredient);

    Product findProductById(Long id);
}
