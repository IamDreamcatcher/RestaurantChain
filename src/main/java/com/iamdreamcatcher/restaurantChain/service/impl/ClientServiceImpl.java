package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.users.ClientDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ClientRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.users.ClientMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.order.Order;
import com.iamdreamcatcher.restaurantChain.model.user.Role;
import com.iamdreamcatcher.restaurantChain.model.user.Status;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.AdministratorRepository;
import com.iamdreamcatcher.restaurantChain.repository.ClientRepository;
import com.iamdreamcatcher.restaurantChain.repository.OrderRepository;
import com.iamdreamcatcher.restaurantChain.repository.UserRepository;
import com.iamdreamcatcher.restaurantChain.security.AuthContextHandler;
import com.iamdreamcatcher.restaurantChain.service.ClientService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ClientMapper clientMapper;
    private final AuthContextHandler authContextHandler;
    private final AdministratorRepository administratorRepository;
    private final OrderRepository orderRepository;

    @Override
    public ClientDTO register(ClientRequestDTO request) throws RegistrationException {
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

    @Override
    public ClientDTO changeClientAccountStatus(Long id, ClientRequestDTO clientRequestDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        User user = authContextHandler.getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User is not admin");
        }
        Administrator administrator = administratorRepository.findByUser(user);
        List<Order> orders = orderRepository.findOrdersByAdministrator(administrator);
        Client client = null;
        for(Order order: orders) {
            if (order.getClient().getId() == id) {
                client = order.getClient();
            }
        }
        if (client == null) {
            throw new NotFoundException("That client doesn't exists");
        }

        client.getUser().setStatus(clientRequestDTO.status());
        clientRepository.save(client);

        return clientMapper.toClientDto(client);
    }

    private User getUserFromRegisterRequest(ClientRequestDTO request) {
        User user = new User();

        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setStatus(request.status());
        user.setRole(request.role());
        user.setName(request.name());

        return user;
    }
}
