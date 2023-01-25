package com.iamdreamcatcher.restaurantChain.controller;

import com.iamdreamcatcher.restaurantChain.dto.model.CartItemDTO;
import com.iamdreamcatcher.restaurantChain.dto.response.RestApiResponse;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.service.CartService;
import com.iamdreamcatcher.restaurantChain.service.ProductService;
import com.iamdreamcatcher.restaurantChain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class MainController {
    private final RestaurantService restaurantService;
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getRestaurants() {
        return ResponseEntity.ok(new RestApiResponse("ok", restaurantService.getRestaurants()));
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(new RestApiResponse("ok", restaurantService.getRestaurantById(id)));
    }

    @GetMapping("/restaurant/{id}/products")
    public ResponseEntity<?> getRestaurantProducts(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.getRestaurantProductsForClient(id)));
    }

    @GetMapping("/restaurant/{id}/products/{pId}")
    public ResponseEntity<?> getRestaurantProductById(@PathVariable Long id, @PathVariable Long pId) throws NotFoundException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.getProductByIdForClient(id, pId)));
    }

    @PostMapping("/restaurant/{id}/products/{pId}/add-to-shopping-cart")
    public ResponseEntity<?> addProductToShoppingCart(@PathVariable Long id, @PathVariable Long pId,
                                                      @RequestBody CartItemDTO cartItemDTO) throws NotFoundException, UserNotLoggedInException {
        cartService.addProductToShoppingCart(id, pId, cartItemDTO);
        return ResponseEntity.ok(new RestApiResponse("a product with id = %d has been added to cart".formatted(id)));
    }
    //To do:remove, clear, checkout cart

    //To do: possible to get a job tru get mail
    //To do: find restaurants where getting product exists
}
