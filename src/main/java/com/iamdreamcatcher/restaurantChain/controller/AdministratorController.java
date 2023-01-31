package com.iamdreamcatcher.restaurantChain.controller;

import com.iamdreamcatcher.restaurantChain.dto.request.*;
import com.iamdreamcatcher.restaurantChain.dto.response.RestApiResponse;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.model.order.OrderStatus;
import com.iamdreamcatcher.restaurantChain.service.*;
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
    private final ReviewsService reviewsService;
    private final ClientService clientService;
    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<?> getAdminRestaurant() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", restaurantService.getAdminRestaurant()));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAdminProducts() throws NoPermissionException, UserNotLoggedInException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.getRestaurantProductsForAdmin()));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getAdminProducts(@PathVariable Long id) throws NoPermissionException, UserNotLoggedInException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.getProductByIdForAdmin(id)));
    }

    @PostMapping("/products/new")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO productDTO) throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", productService.createProduct(productDTO)));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse(
                "a product with id = %d has been updated".formatted(id),
                productService.updateProduct(id, productDTO)));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        productService.deleteProductById(id);

        return ResponseEntity.ok(new RestApiResponse("a product with id = %d has been deleted".formatted(id)));
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
                "a cook account with id = %d has been updated".formatted(id),
                cookService.updateCookAccount(id, cookRequestDTO)));
    }

    @DeleteMapping("/cooks/{id}")
    public ResponseEntity<?> deleteCookAccount(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        cookService.deleteCookById(id);

        return ResponseEntity.ok(new RestApiResponse("a cooks with id = %d has been deleted".formatted(id)));
    }

    @GetMapping("/couriers")
    public ResponseEntity<?> getCouriers() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", courierService.getRestaurantCouriers()));
    }

    @GetMapping("/couriers/{id}")
    public ResponseEntity<?> getCourierById(@PathVariable Long id) throws NoPermissionException, UserNotLoggedInException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse("ok", courierService.getCourierById(id)));
    }

    @PostMapping("/couriers/new")
    public ResponseEntity<?> createCourierAccount(@RequestBody CourierRequestDTO courierRequestDTO) throws UserNotLoggedInException, NoPermissionException, RegistrationException {
        return ResponseEntity.ok(new RestApiResponse("ok", courierService.createCourierAccount(courierRequestDTO)));
    }

    @PutMapping("/couriers/{id}")
    public ResponseEntity<?> updateCourierAccount(@PathVariable Long id, @RequestBody CourierRequestDTO courierRequestDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse(
                "a courier account with id = %d has been updated".formatted(id),
                courierService.updateCourierAccount(id, courierRequestDTO)));
    }

    @DeleteMapping("/couriers/{id}")
    public ResponseEntity<?> deleteCourierAccount(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        courierService.deleteCourierById(id);

        return ResponseEntity.ok(new RestApiResponse("a courier with id = %d has been deleted".formatted(id)));
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> getReviews() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", reviewsService.getRestaurantReviews()));
    }
    @GetMapping("/reviews/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse("ok", reviewsService.getReviewById(id)));
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<?> leaveComment(@PathVariable Long id, @RequestBody ReviewRequestDto reviewRequestDto) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse(
                "a review with id = %d has been updated with comment".formatted(id),
                reviewsService.leaveComment(id, reviewRequestDto)));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReviewById(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        reviewsService.deleteReviewById(id);

        return ResponseEntity.ok(new RestApiResponse("a review with id = %d has been deleted".formatted(id)));
    }

    @GetMapping("/clients")
    public ResponseEntity<?> getRestaurantClients() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", restaurantService.getRestaurantClients()));
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> changeClientAccountStatus(@PathVariable Long id, @RequestBody ClientRequestDTO clientRequestDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse(
                "a client with id = %d has been updated with new status".formatted(id),
                clientService.changeClientAccountStatus(id, clientRequestDTO)));
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrdersForAdmin() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", orderService.getOrdersForAdmin()));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderForAdmin(@PathVariable Long id) throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", orderService.getOrderForAdmin(id)));
    }

    @GetMapping("/orders/{id}/change-status")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long id, @RequestBody OrderStatus orderStatus) throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        orderService.changeOrderStatus(id, orderStatus);
        return ResponseEntity.ok(new RestApiResponse("an order with id = %d has been updated with new status".formatted(id)));
    }
}
