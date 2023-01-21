package com.iamdreamcatcher.restaurantChain.advice;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseMessage {
    USER_NOT_LOGGED_IN_ERROR("user is not logged in error"),
    REGISTRATION_ERROR("invalid registration form data"),
    NO_PERMISSION_ERROR("user has no permission");

    public final String message;
}
