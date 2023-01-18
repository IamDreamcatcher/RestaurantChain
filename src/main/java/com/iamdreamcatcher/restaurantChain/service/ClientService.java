package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.ClientDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ClientRegisterRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;

public interface ClientService {
    ClientDTO register(ClientRegisterRequestDTO request) throws RegistrationException;
}
