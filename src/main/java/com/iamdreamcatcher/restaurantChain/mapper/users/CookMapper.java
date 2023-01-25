package com.iamdreamcatcher.restaurantChain.mapper.users;

import com.iamdreamcatcher.restaurantChain.dto.model.users.CookDTO;
import com.iamdreamcatcher.restaurantChain.mapper.RestaurantMapper;
import com.iamdreamcatcher.restaurantChain.model.cook.Cook;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CookMapper {
    private final RestaurantMapper restaurantMapper;
    public CookDTO toCookDto(Cook cook) {
        CookDTO cookDTO = new CookDTO();
        cookDTO.setId(cook.getId());
        cookDTO.setStatus(cook.getUser().getStatus());
        cookDTO.setNumber(cook.getNumber());
        cookDTO.setRole(cook.getUser().getRole());
        cookDTO.setEmail(cook.getUser().getEmail());
        cookDTO.setRestaurantDTO(restaurantMapper.toRestaurantDTO(cook.getRestaurant()));

        return cookDTO;
    }

    public List<CookDTO> toCookListDto(List<Cook> cooks) {
        List<CookDTO> cookDTOList = new ArrayList<>();
        for (Cook cook: cooks) {
            cookDTOList.add(toCookDto(cook));
        }
        return cookDTOList;
    }
}