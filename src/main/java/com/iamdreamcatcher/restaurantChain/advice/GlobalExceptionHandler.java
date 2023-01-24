package com.iamdreamcatcher.restaurantChain.advice;

import com.iamdreamcatcher.restaurantChain.dto.model.ErrorDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserNotLoggedInException.class)
    public ResponseEntity<ErrorDTO> handler(final UserNotLoggedInException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorDTO(
                        HttpStatus.BAD_REQUEST.name(),
                        ResponseMessage.USER_NOT_LOGGED_IN_ERROR.message,
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<ErrorDTO> handler(final RegistrationException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorDTO(
                        HttpStatus.BAD_REQUEST.name(),
                        ResponseMessage.REGISTRATION_ERROR.message,
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(value = NoPermissionException.class)
    public ResponseEntity<ErrorDTO> handler(final NoPermissionException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorDTO(
                        HttpStatus.BAD_REQUEST.name(),
                        ResponseMessage.NO_PERMISSION_ERROR.message,
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorDTO> handler(final NotFoundException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorDTO(
                        HttpStatus.BAD_REQUEST.name(),
                        ResponseMessage.NOT_FOUND_ERROR.message,
                        exception.getMessage()
                ));
    }
}
