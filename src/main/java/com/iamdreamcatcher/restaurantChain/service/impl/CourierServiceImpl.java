package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.users.CourierDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.CourierRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.users.CourierMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.courier.Courier;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.user.Role;
import com.iamdreamcatcher.restaurantChain.model.user.Status;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.CourierRepository;
import com.iamdreamcatcher.restaurantChain.repository.UserRepository;
import com.iamdreamcatcher.restaurantChain.service.AdministratorService;
import com.iamdreamcatcher.restaurantChain.service.CourierService;
import com.iamdreamcatcher.restaurantChain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final RestaurantService restaurantService;
    private final CourierRepository courierRepository;
    private final CourierMapper courierMapper;
    private final AdministratorService administratorService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<CourierDTO> getRestaurantCouriers() throws UserNotLoggedInException, NoPermissionException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        List<Courier> couriers = courierRepository.findAllCouriersByRestaurant(restaurant);

        return courierMapper.toCourierListDto(couriers);
    }

    @Override
    public CourierDTO getCourierById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Administrator administrator = administratorService.getAdmin();
        Courier courier = courierRepository.findCourierById(id);
        if (courier == null) {
            throw new NotFoundException("Courier not found");
        }
        if (administrator.getRestaurant().getId() != courier.getRestaurant().getId()) {
            throw new NoPermissionException("That courier doesn't belong to this admin");
        }
        return courierMapper.toCourierDto(courier);
    }

    @Override
    public CourierDTO createCourierAccount(CourierRequestDTO courierRequestDTO) throws UserNotLoggedInException, NoPermissionException, RegistrationException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        Courier courier = registerCourier(courierRequestDTO);
        courier.setRestaurant(restaurant);
        courierRepository.save(courier);

        return courierMapper.toCourierDto(courier);
    }


    @Override
    public CourierDTO updateCourierAccount(Long id, CourierRequestDTO courierRequestDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        Courier courier = courierRepository.findCourierById(id);
        if (courier == null) {
            throw new NotFoundException("Cook not found");
        }
        if (courier.getRestaurant().getId() != restaurant.getId()) {
            throw new NoPermissionException("Admin have no permissions to change this cook account");
        }
        if (courierRequestDTO.name() != null) {
            courier.getUser().setName(courierRequestDTO.name());
        }
        if (courierRequestDTO.email() != null) {
            courier.getUser().setEmail(courierRequestDTO.email());
        }
        if (courierRequestDTO.number() != null) {
            courier.setNumber(courierRequestDTO.number());
        }
        if (courierRequestDTO.password() != null) {
            courier.getUser().setPassword(courierRequestDTO.password());
        }

        courierRepository.save(courier);
        return courierMapper.toCourierDto(courier);
    }

    @Override
    public void deleteCourierById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        Courier courier = courierRepository.findCourierById(id);
        if (courier == null) {
            throw new NotFoundException("Courier not found");
        }
        if (courier.getRestaurant().getId() != restaurant.getId()) {
            throw new NoPermissionException("Admin have no permissions to delete this courier");
        }

        courierRepository.deleteById(id);
    }

    private Courier registerCourier(CourierRequestDTO courierRequestDTO) throws RegistrationException {
        if (StringUtils.isEmpty(courierRequestDTO.email()) || userRepository.existsByEmail(courierRequestDTO.email())) {
            throw new RegistrationException("invalid data, email already exists");
        }

        if (!EnumUtils.isValidEnum(Status.class, courierRequestDTO.status().name())) {
            throw new RegistrationException("invalid data, wrong account status");
        }

        if (!EnumUtils.isValidEnum(Role.class, courierRequestDTO.role().name()) && courierRequestDTO.role() == Role.EMPLOYEE) {
            throw new RegistrationException("invalid data, wrong account role");
        }
        //To do: add regex for phone
        if (courierRequestDTO.number() == null) {
            throw new RegistrationException("invalid data, wrong account phone");
        }

        User newUser = new User();
        newUser.setEmail(courierRequestDTO.email());
        newUser.setPassword(passwordEncoder.encode(courierRequestDTO.password()));
        newUser.setStatus(courierRequestDTO.status());
        newUser.setRole(courierRequestDTO.role());
        newUser.setName(courierRequestDTO.name());
        userRepository.save(newUser);

        Courier courier = new Courier();
        courier.setUser(newUser);
        courier.setNumber(courierRequestDTO.number());

        return courier;
    }
}
