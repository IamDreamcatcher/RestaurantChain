package com.iamdreamcatcher.restaurantChain.advice;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseMessage {
    USER_NOT_LOGGED_IN_ERROR("user is not logged in error"),
    REGISTRATION_ERROR("invalid form data");

    public final String message;
}
