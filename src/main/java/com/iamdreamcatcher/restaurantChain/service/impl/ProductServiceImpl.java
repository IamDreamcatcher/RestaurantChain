package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.IngredientDTO;
import com.iamdreamcatcher.restaurantChain.dto.model.ProductDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ProductCreationRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.ProductMapper;
import com.iamdreamcatcher.restaurantChain.model.ingredient.Ingredient;
import com.iamdreamcatcher.restaurantChain.model.product.Product;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.user.Role;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.IngredientRepository;
import com.iamdreamcatcher.restaurantChain.repository.ProductRepository;
import com.iamdreamcatcher.restaurantChain.security.AuthContextHandler;
import com.iamdreamcatcher.restaurantChain.service.ProductService;
import com.iamdreamcatcher.restaurantChain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final RestaurantService restaurantService;
    private final ProductRepository productRepository;
    private final AuthContextHandler authContextHandler;
    private final IngredientRepository ingredientRepository;

    @Override
    public Iterable<ProductDTO> getAdminProducts() throws NoPermissionException, UserNotLoggedInException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();

        return productMapper.toProductDTOList(productRepository.findProductsByRestaurant(restaurant));
    }

    @Override
    public ProductDTO getAdminProductById(Long id) throws UserNotLoggedInException, NoPermissionException {
        User user = authContextHandler.getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User is not admin");
        }
        Product product = productRepository.findProductById(id);

        return productMapper.toProductDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductCreationRequestDTO productDTO) throws UserNotLoggedInException, NoPermissionException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        Product product = new Product(productDTO.name(), productDTO.description(),
                productDTO.price(), restaurant, getIngredientsListFromRequest(productDTO));
        productRepository.save(product);

        return productMapper.toProductDTO(product);
    }

    private List<Ingredient> getIngredientsListFromRequest(ProductCreationRequestDTO productDTO) {
        List<Ingredient> result = new ArrayList<>();
        for(IngredientDTO ingredientDTO: productDTO.ingredients()) {
            Ingredient ingredient = ingredientRepository.findIngredientByName(ingredientDTO.getName());
            if (ingredient == null) {
                ingredient = new Ingredient();
                ingredient.setName(ingredientDTO.getName());
                ingredientRepository.save(ingredient);
            }
            result.add(ingredient);
        }

        return result;
    }
}