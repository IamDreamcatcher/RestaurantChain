package com.iamdreamcatcher.restaurantChain.dto.request;

import com.iamdreamcatcher.restaurantChain.dto.model.IngredientDTO;

import java.util.List;

public record ProductRequestDTO(
        String name,
        String description,
        Double price,
        List<IngredientDTO> ingredients
) {

}
