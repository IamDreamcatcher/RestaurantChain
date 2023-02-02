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
@RequestMapping("cook")
@AllArgsConstructor
public class CookController {
    private final OrderService orderService;
    @GetMapping("/orders")
    public ResponseEntity<?> getOrdersForCook() throws UserNotLoggedInException, NoPermissionException {
        return ResponseEntity.ok(new RestApiResponse("ok", orderService.getOrdersForCook()));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderForCook(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        return ResponseEntity.ok(new RestApiResponse("ok", orderService.getOrderForCook(id)));
    }

    @PostMapping("/orders/{id}/cooking")
    public ResponseEntity<?> startCooking(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        orderService.startCooking(id);

        return ResponseEntity.ok(new RestApiResponse("Order with id = %d has started to be prepared".formatted(id)));
    }

    @PostMapping("/orders/{id}/prepared")
    public ResponseEntity<?> finishingCooking(@PathVariable Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        orderService.finishingCooking(id);

        return ResponseEntity.ok(new RestApiResponse("Order with id = %d has prepared".formatted(id)));
    }
}
