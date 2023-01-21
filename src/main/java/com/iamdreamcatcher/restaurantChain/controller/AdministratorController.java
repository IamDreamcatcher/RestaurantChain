package com.iamdreamcatcher.restaurantChain.controller;

import com.iamdreamcatcher.restaurantChain.dto.request.ProductRequestDTO;
import com.iamdreamcatcher.restaurantChain.dto.response.RestApiResponse;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.service.EmployeeService;
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
    private final EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<?> getAdminRestaurant() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", restaurantService.getAdminRestaurant()));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAdminProducts() throws NoPermissionException, UserNotLoggedInException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.getAdminProducts()));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getAdminProducts(@PathVariable Long id) throws NoPermissionException, UserNotLoggedInException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.getAdminProductById(id)));
    }

    @PostMapping("/products/new")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO productDTO) throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.createProduct(productDTO)));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productDTO) throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse(
                "an product with id = %d has been updated".formatted(id),
                productService.updateProduct(id, productDTO)));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException {
        productService.deleteProductById(id);

        return ResponseEntity.ok(new RestApiResponse("an product with id = %d has been deleted".formatted(id)));
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getEmployees() {
        return ResponseEntity.ok(new RestApiResponse("ok", employeeService.getRestaurantEmployees()));

        //use map for this;
    }
}
