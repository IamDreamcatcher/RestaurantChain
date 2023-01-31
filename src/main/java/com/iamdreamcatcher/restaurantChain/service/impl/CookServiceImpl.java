package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.users.CookDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.CookRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.RegistrationException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.users.CookMapper;
import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.cook.Cook;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.user.Role;
import com.iamdreamcatcher.restaurantChain.model.user.Status;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.CookRepository;
import com.iamdreamcatcher.restaurantChain.repository.UserRepository;
import com.iamdreamcatcher.restaurantChain.service.AdministratorService;
import com.iamdreamcatcher.restaurantChain.service.CookService;
import com.iamdreamcatcher.restaurantChain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class CookServiceImpl implements CookService {
    private final RestaurantService restaurantService;
    private final CookRepository cookRepository;
    private final CookMapper cookMapper;
    private final AdministratorService administratorService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<CookDTO> getRestaurantCooks() throws UserNotLoggedInException, NoPermissionException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        List<Cook> cooks = cookRepository.findAllCooksByRestaurant(restaurant);

        return cookMapper.toCookListDto(cooks);
    }

    @Override
    public CookDTO getCookById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Administrator administrator = administratorService.getAdmin();
        Cook cook = cookRepository.findCookById(id);
        if (cook == null) {
            throw new NotFoundException("Cook not found");
        }
        if (administrator.getRestaurant().getId() != cook.getRestaurant().getId()) {
            throw new NoPermissionException("That cook doesn't suit to this admin");
        }
        return cookMapper.toCookDto(cook);
    }

    @Override
    public CookDTO createCookAccount(CookRequestDTO cookRequestDTO) throws UserNotLoggedInException, NoPermissionException, RegistrationException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        Cook cook = registerCook(cookRequestDTO);
        cook.setRestaurant(restaurant);
        cookRepository.save(cook);

        return cookMapper.toCookDto(cook);
    }

    @Override
    public CookDTO updateCookAccount(Long id, CookRequestDTO cookRequestDTO) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        Cook cook = cookRepository.findCookById(id);
        if (cook == null) {
            throw new NotFoundException("Cook not found");
        }
        if (cook.getRestaurant().getId() != restaurant.getId()) {
            throw new NoPermissionException("Admin have no permissions to change this cook account");
        }
        if (cookRequestDTO.name() != null) {
            cook.getUser().setName(cookRequestDTO.name());
        }
        if (cookRequestDTO.email() != null) {
            cook.getUser().setEmail(cookRequestDTO.email());
        }
        if (cookRequestDTO.number() != null) {
            cook.setNumber(cookRequestDTO.number());
        }
        if (cookRequestDTO.password() != null) {
            cook.getUser().setPassword(cookRequestDTO.password());
        }

        cookRepository.save(cook);
        return cookMapper.toCookDto(cook);
    }

    @Override
    public void deleteCookById(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException {
        Restaurant restaurant = restaurantService.getAdminRestaurant();
        Cook cook = cookRepository.findCookById(id);
        if (cook == null) {
            throw new NotFoundException("Cook not found");
        }
        if (cook.getRestaurant().getId() != restaurant.getId()) {
            throw new NoPermissionException("Admin have no permissions to delete this cook");
        }

        cookRepository.deleteById(id);
    }

    private Cook registerCook(CookRequestDTO cookRequestDTO) throws RegistrationException {
        if (StringUtils.isEmpty(cookRequestDTO.email()) || userRepository.existsByEmail(cookRequestDTO.email())) {
            throw new RegistrationException("invalid data, email already exists");
        }

        if (!EnumUtils.isValidEnum(Status.class, cookRequestDTO.status().name())) {
            throw new RegistrationException("invalid data, wrong account status");
        }

        if (!EnumUtils.isValidEnum(Role.class, cookRequestDTO.role().name()) && cookRequestDTO.role() == Role.EMPLOYEE) {
            throw new RegistrationException("invalid data, wrong account role");
        }
        //To do: add regex for phone
        if (cookRequestDTO.number() == null) {
            throw new RegistrationException("invalid data, wrong account phone");
        }

        User newUser = new User();
        newUser.setEmail(cookRequestDTO.email());
        newUser.setPassword(passwordEncoder.encode(cookRequestDTO.password()));
        newUser.setStatus(cookRequestDTO.status());
        newUser.setRole(cookRequestDTO.role());
        newUser.setName(cookRequestDTO.name());
        userRepository.save(newUser);

        Cook cook = new Cook();
        cook.setUser(newUser);
        cook.setNumber(cookRequestDTO.number());

        return cook;
    }
}
