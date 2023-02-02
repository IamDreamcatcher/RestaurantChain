package com.iamdreamcatcher.restaurantChain.controller;

import com.iamdreamcatcher.restaurantChain.dto.response.RestApiResponse;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courier")
@AllArgsConstructor
public class CourierController {
    private final OrderService orderService;
    @GetMapping("/orders")
    public ResponseEntity<?> getOrdersForCourier() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", orderService.getOrdersForCourier()));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderForCourier(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse("ok", orderService.getOrderForCourier(id)));
    }

    @PostMapping("/orders/{id}/delivered")
    public ResponseEntity<?> delivered(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        orderService.delivered(id);

        return ResponseEntity.ok(new RestApiResponse("Order with id = %d has started to be delivered".formatted(id)));
    }

    @PostMapping("/orders/{id}/completed")
    public ResponseEntity<?> finishingCooking(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        orderService.completed(id);

        return ResponseEntity.ok(new RestApiResponse("Order with id = %d has completed".formatted(id)));
    }
}

