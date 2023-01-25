package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.IngredientDTO;
import com.iamdreamcatcher.restaurantChain.dto.model.ProductDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ProductRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.ProductMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.ingredient.Ingredient;
import com.iamdreamcatcher.restaurantChain.model.product.Product;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.user.Role;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.AdministratorRepository;
import com.iamdreamcatcher.restaurantChain.repository.IngredientRepository;
import com.iamdreamcatcher.restaurantChain.repository.ProductRepository;
import com.iamdreamcatcher.restaurantChain.repository.RestaurantRepository;
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

    private final AdministratorRepository administratorRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Iterable<ProductDTO> getRestaurantProductsForAdmin() throws NoPermissionException, UserNotLoggedInException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();

        return productMapper.toProductDTOList(productRepository.findProductsByRestaurant(restaurant));
    }

    @Override
    public ProductDTO getProductByIdForAdmin(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        User user = authContextHandler.getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User is not admin");
        }
        Administrator administrator = administratorRepository.findByUser(user);
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        if (administrator.getRestaurant().getId() != product.getRestaurant().getId()) {
            throw new NoPermissionException("That product doesn't belong to this admin");
        }
        return productMapper.toProductDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductRequestDTO productDTO) throws UserNotLoggedInException, NoPermissionException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        Product product = new Product(productDTO.name(), productDTO.description(),
                productDTO.price(), restaurant, getIngredientsListFromRequest(productDTO));
        productRepository.save(product);

        return productMapper.toProductDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductRequestDTO productDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }

        if (product.getRestaurant().getId() != restaurant.getId()) {
            throw new NoPermissionException("Admin have no permissions to change this product");
        }
        if (productDTO.name() != null) {
            product.setName(productDTO.name());
        }
        if (productDTO.description() != null) {
            product.setDescription(productDTO.description());
        }
        if (productDTO.price() != null) {
            product.setPrice(productDTO.price());
        }
        if (!productDTO.ingredients().isEmpty()) {
            product.setIngredients(getIngredientsListFromRequest(productDTO));
        }

        productRepository.save(product);
        return productMapper.toProductDTO(product);
    }

    @Override
    public void deleteProductById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        if (product.getRestaurant().getId() != restaurant.getId()) {
            throw new NoPermissionException("Admin have no permissions to delete this product");
        }

        productRepository.deleteById(id);
    }

    @Override
    public Iterable<ProductDTO> getRestaurantProductsForClient(Long id) throws NotFoundException {
        Restaurant restaurant = restaurantRepository.findRestaurantById(id);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant with such id not found");
        }
        return productMapper.toProductDTOList(productRepository.findProductsByRestaurant(restaurant));
    }

    @Override
    public ProductDTO getProductByIdForClient(Long id, Long pId) throws NotFoundException {
        Restaurant restaurant = restaurantRepository.findRestaurantById(id);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant with such id not found");
        }
        Product product = productRepository.findProductById(pId);
        if (product.getRestaurant().getId() != restaurant.getId()) {
            throw new NotFoundException("This product doesn't belong to restaurant");
        }

        return productMapper.toProductDTO(product);
    }

    private List<Ingredient> getIngredientsListFromRequest(ProductRequestDTO productDTO) {
        List<Ingredient> result = new ArrayList<>();
        for (IngredientDTO ingredientDTO : productDTO.ingredients()) {
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
