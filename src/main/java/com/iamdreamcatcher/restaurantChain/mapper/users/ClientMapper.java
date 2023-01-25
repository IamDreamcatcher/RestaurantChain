package com.iamdreamcatcher.restaurantChain.mapper.users;

import com.iamdreamcatcher.restaurantChain.dto.model.users.ClientDTO;
import com.iamdreamcatcher.restaurantChain.dto.model.users.CookDTO;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.cook.Cook;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<ClientDTO> toClientDtoList(List<Client> clients) {
        List<ClientDTO> clientDTOList = new ArrayList<>();
        for (Client client: clients) {
            clientDTOList.add(toClientDto(client));
        }
        return clientDTOList;
    }
}
