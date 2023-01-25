package com.iamdreamcatcher.restaurantChain.mapper.users;

import com.iamdreamcatcher.restaurantChain.dto.model.users.CourierDTO;
import com.iamdreamcatcher.restaurantChain.mapper.RestaurantMapper;
import com.iamdreamcatcher.restaurantChain.model.courier.Courier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CourierMapper {
    private final RestaurantMapper restaurantMapper;

    public CourierDTO toCourierDto(Courier courier) {
        CourierDTO courierDTO = new CourierDTO();
        courierDTO.setId(courier.getId());
        courierDTO.setStatus(courier.getUser().getStatus());
        courierDTO.setNumber(courier.getNumber());
        courierDTO.setRole(courier.getUser().getRole());
        courierDTO.setEmail(courier.getUser().getEmail());
        courierDTO.setRestaurantDTO(restaurantMapper.toRestaurantDTO(courier.getRestaurant()));

        return courierDTO;
    }

    public List<CourierDTO> toCourierListDto(List<Courier> couriers) {
        List<CourierDTO> courierDTOList = new ArrayList<>();
        for (Courier courier : couriers) {
            courierDTOList.add(toCourierDto(courier));
        }
        return courierDTOList;
    }
}