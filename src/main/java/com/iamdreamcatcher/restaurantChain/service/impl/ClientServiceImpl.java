package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.ClientDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ClientRegisterRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.mapper.ClientMapper;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.user.Role;
import com.iamdreamcatcher.restaurantChain.model.user.Status;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.ClientRepository;
import com.iamdreamcatcher.restaurantChain.repository.UserRepository;
import com.iamdreamcatcher.restaurantChain.service.ClientService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ClientMapper clientMapper;
    @Override
    public ClientDTO register(ClientRegisterRequestDTO request) throws RegistrationException {
        if (StringUtils.isEmpty(request.email()) || userRepository.existsByEmail(request.email())) {
            throw new RegistrationException("invalid data, email already exists");
        }

        if (!EnumUtils.isValidEnum(Status.class, request.status().name())) {
            throw new RegistrationException("invalid data, wrong account status");
        }

        if (!EnumUtils.isValidEnum(Role.class, request.role().name())) {
            throw new RegistrationException("invalid data, wrong account role");
        }
        //To do: add regex for phone
        if (request.number() == null) {
            throw new RegistrationException("invalid data, wrong account phone");
        }

        User newUser = getUserFromRegisterRequest(request);
        userRepository.save(newUser);

        Client newClient = new Client();
        newClient.setNumber(request.number());
        newClient.setUser(newUser);

        clientRepository.save(newClient);

        return clientMapper.toClientDto(newClient);
    }

    private User getUserFromRegisterRequest(ClientRegisterRequestDTO request) {
        User user = new User();

        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setStatus(request.status());
        user.setRole(request.role());
        user.setName(request.name());

        return user;
    }
}
