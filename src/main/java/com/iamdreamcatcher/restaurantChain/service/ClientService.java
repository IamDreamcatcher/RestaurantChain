package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.users.ClientDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ClientRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.model.client.Client;

public interface ClientService {
    Client getClient() throws UserNotLoggedInException, NoPermissionException;

    ClientDTO register(ClientRequestDTO request) throws RegistrationException;

    ClientDTO changeClientAccountStatus(Long id, ClientRequestDTO clientRequestDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException;
}
