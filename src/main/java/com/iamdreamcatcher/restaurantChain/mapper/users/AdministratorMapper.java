package com.iamdreamcatcher.restaurantChain.mapper.users;

import com.iamdreamcatcher.restaurantChain.dto.model.users.AdministratorDTO;
import com.iamdreamcatcher.restaurantChain.mapper.RestaurantMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class AdministratorMapper {
    private final RestaurantMapper restaurantMapper;

    public AdministratorDTO toAdministratorDTO(Administrator administrator) {
        AdministratorDTO administratorDTO = new AdministratorDTO();
        administratorDTO.setId(administrator.getId());
        administratorDTO.setStatus(administrator.getUser().getStatus());
        administratorDTO.setNumber(administrator.getNumber());
        administratorDTO.setRole(administrator.getUser().getRole());
        administratorDTO.setEmail(administrator.getUser().getEmail());
        administratorDTO.setRestaurantDTO(restaurantMapper.toRestaurantDTO(administrator.getRestaurant()));

        return administratorDTO;
    }

    public List<AdministratorDTO> toAdministratorListDTOList(List<Administrator> administrators) {
        List<AdministratorDTO> administratorDTOList = new ArrayList<>();
        for (Administrator administrator : administrators) {
            administratorDTOList.add(toAdministratorDTO(administrator));
        }
        return administratorDTOList;
    }
}