package com.iamdreamcatcher.restaurantChain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiResponse {
    private String message;
    private Object body;

    public RestApiResponse(String message) {
        this.message = message;
    }
    public RestApiResponse(String message, Object body) {
        this.message = message;
        this.body = body;
    }
}
