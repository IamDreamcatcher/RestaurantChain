package com.iamdreamcatcher.restaurantChain.exception;

public class UserNotLoggedInException extends Exception {
    public UserNotLoggedInException() {
        super();
    }
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
