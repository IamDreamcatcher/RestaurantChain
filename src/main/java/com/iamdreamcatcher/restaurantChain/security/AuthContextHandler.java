package com.iamdreamcatcher.restaurantChain.security;

import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthContextHandler {
    private final UserRepository userRepository;
    public User getLoggedInUser() throws UserNotLoggedInException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotLoggedInException();
        } else {
            return user;
        }
    }
}
