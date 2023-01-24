package com.iamdreamcatcher.restaurantChain.controller;

import com.iamdreamcatcher.restaurantChain.dto.request.CookRequestDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ProductRequestDTO;
import com.iamdreamcatcher.restaurantChain.dto.response.RestApiResponse;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.service.CookService;
import com.iamdreamcatcher.restaurantChain.service.CourierService;
import com.iamdreamcatcher.restaurantChain.service.ProductService;
import com.iamdreamcatcher.restaurantChain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdministratorController {
    private final RestaurantService restaurantService;
    private final ProductService productService;
    private final CookService cookService;
    private final CourierService courierService;

    @GetMapping()
    public ResponseEntity<?> getAdminRestaurant() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", restaurantService.getAdminRestaurant()));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAdminProducts() throws NoPermissionException, UserNotLoggedInException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.getRestaurantProducts()));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getAdminProducts(@PathVariable Long id) throws NoPermissionException, UserNotLoggedInException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.getProductById(id)));
    }

    @PostMapping("/products/new")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO productDTO) throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.createProduct(productDTO)));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse(
                "an product with id = %d has been updated".formatted(id),
                productService.updateProduct(id, productDTO)));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        productService.deleteProductById(id);

        return ResponseEntity.ok(new RestApiResponse("an product with id = %d has been deleted".formatted(id)));
    }

    @GetMapping("/cooks")
    public ResponseEntity<?> getCooks() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", cookService.getRestaurantCooks()));
    }

    @GetMapping("/cooks/{id}")
    public ResponseEntity<?> getCookById(@PathVariable Long id) throws NoPermissionException, UserNotLoggedInException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse("ok", cookService.getCookById(id)));
    }

    @PostMapping("/cooks/new")
    public ResponseEntity<?> createCookAccount(@RequestBody CookRequestDTO cookRequestDTO) throws UserNotLoggedInException, NoPermissionException, RegistrationException {
        return ResponseEntity.ok(new RestApiResponse("ok", cookService.createCookAccount(cookRequestDTO)));
    }

    @PutMapping("/cooks/{id}")
    public ResponseEntity<?> updateCookAccount(@PathVariable Long id, @RequestBody CookRequestDTO cookRequestDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse(
                "an cook account with id = %d has been updated".formatted(id),
                cookService.updateCookAccount(id, cookRequestDTO)));
    }


    @GetMapping("/couriers")
    public ResponseEntity<?> getCouriers() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", courierService.getRestaurantCouriers()));
    }
}
