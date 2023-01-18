package com.iamdreamcatcher.restaurantChain.mapper;

import com.iamdreamcatcher.restaurantChain.dto.model.ClientDTO;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public ClientDTO toClientDto(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setStatus(client.getUser().getStatus());
        clientDTO.setNumber(client.getNumber());
        clientDTO.setRole(client.getUser().getRole());
        clientDTO.setEmail(client.getUser().getEmail());

        return clientDTO;
    }
}
