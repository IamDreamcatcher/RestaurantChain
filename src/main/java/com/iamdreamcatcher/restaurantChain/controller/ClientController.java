package com.iamdreamcatcher.restaurantChain.controller;

import com.iamdreamcatcher.restaurantChain.dto.model.CartItemDTO;
import com.iamdreamcatcher.restaurantChain.dto.response.RestApiResponse;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.service.CartService;
import com.iamdreamcatcher.restaurantChain.service.OrderService;
import com.iamdreamcatcher.restaurantChain.service.ProductService;
import com.iamdreamcatcher.restaurantChain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ClientController {
    private final RestaurantService restaurantService;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;

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
                                                      @RequestBody CartItemDTO cartItemDTO) throws NotFoundException, UserNotLoggedInException, NoPermissionException {
        cartService.addProductToShoppingCart(id, pId, cartItemDTO);
        return ResponseEntity.ok(new RestApiResponse("a product with id = %d has been added to cart".formatted(pId)));
    }

    @PostMapping("/restaurant/{id}/products/{pId}/remove-from-shopping-cart")
    public ResponseEntity<?> removeProductFromShoppingCart(@PathVariable Long id, @PathVariable Long pId,
                                                           @RequestBody CartItemDTO cartItemDTO) throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        cartService.removeProductFromShoppingCart(id, pId, cartItemDTO);
        return ResponseEntity.ok(new RestApiResponse("a product with id = %d has been deleted from cart".formatted(pId)));
    }

    @GetMapping("/shopping-cart")
    public ResponseEntity<?> getShoppingCart() throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", cartService.getShoppingCart()));
    }

    @PostMapping("shopping-cart/clear")
    public ResponseEntity<?> clearShoppingCart() throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        cartService.clearShoppingCart();
        return ResponseEntity.ok(new RestApiResponse("Cart has been emptied"));
    }

    @PostMapping("/shopping-cart/checkout")
    public ResponseEntity<?> checkout() throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        cartService.checkout();
        return ResponseEntity.ok(new RestApiResponse("New order has been formed"));
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrdersForClient() throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", orderService.getOrdersForClient()));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderForClient(@PathVariable Long id) throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", orderService.getOrderForClient(id)));
    }

    //To do: possible to get a job tru get mail
    //To do: find restaurants where getting product exists
}
