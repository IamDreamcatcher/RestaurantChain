package com.iamdreamcatcher.restaurantChain.controller;

import com.iamdreamcatcher.restaurantChain.dto.request.ClientRegisterRequestDTO;
import com.iamdreamcatcher.restaurantChain.dto.response.RestApiResponse;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ClientRegisterRequestDTO request) throws RegistrationException {
        return ResponseEntity.ok(new RestApiResponse("user is registered", clientService.register(request)));
    }
    //To do: possible to get a job tru get mail
    //To do: find restaurants where getting product exists
}
