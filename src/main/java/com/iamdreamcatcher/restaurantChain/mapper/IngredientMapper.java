package com.iamdreamcatcher.restaurantChain.mapper;

import com.iamdreamcatcher.restaurantChain.dto.model.IngredientDTO;
import com.iamdreamcatcher.restaurantChain.model.ingredient.Ingredient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    List<IngredientDTO> toIngredientDTOList(List<Ingredient> ingredientList);

    IngredientDTO toIngredientDTO(Ingredient ingredient);
}