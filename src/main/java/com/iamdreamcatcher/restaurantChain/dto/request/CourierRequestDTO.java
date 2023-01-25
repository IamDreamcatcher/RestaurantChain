package com.iamdreamcatcher.restaurantChain.dto.request;

import com.iamdreamcatcher.restaurantChain.model.user.Role;
import com.iamdreamcatcher.restaurantChain.model.user.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record CourierRequestDTO(
        String email,
        String password,
        String name,
        String number,
        @Enumerated(EnumType.STRING)
        Role role,
        @Enumerated(EnumType.STRING)
        Status status
) {

}