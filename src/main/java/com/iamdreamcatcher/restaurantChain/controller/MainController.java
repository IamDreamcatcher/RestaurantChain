package com.iamdreamcatcher.restaurantChain.controller;

import com.iamdreamcatcher.restaurantChain.dto.response.RestApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class MainController {
    @GetMapping
    public ResponseEntity<?> getRestaurants() {
        int[] list = new int[]{1, 2, 3, 4};
        return ResponseEntity.ok(new RestApiResponse("list of restaurants", list));
    }
}
