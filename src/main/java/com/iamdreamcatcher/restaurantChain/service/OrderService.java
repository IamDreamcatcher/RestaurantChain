package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.OrderDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.model.order.OrderStatus;

public interface OrderService {
    Iterable<OrderDTO> getOrdersForClient() throws UserNotLoggedInException, NotFoundException, NoPermissionException;

    OrderDTO getOrderForClient(Long id) throws UserNotLoggedInException, NotFoundException, NoPermissionException;

    Iterable<OrderDTO>  getOrdersForAdmin() throws UserNotLoggedInException, NoPermissionException;

    OrderDTO getOrderForAdmin(Long id) throws UserNotLoggedInException, NoPermissionException, NotFoundException;

    void changeOrderStatus(Long id, OrderStatus orderStatus) throws UserNotLoggedInException, NoPermissionException, NotFoundException;
}
