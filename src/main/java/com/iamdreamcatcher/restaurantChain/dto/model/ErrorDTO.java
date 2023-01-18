package com.iamdreamcatcher.restaurantChain.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ErrorDTO {
    private final String status;
    private final String title;
    private final String details;
}
