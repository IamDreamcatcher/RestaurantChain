package com.iamdreamcatcher.restaurantChain.service;

import com.iamdreamcatcher.restaurantChain.dto.model.ProductDTO;
import com.iamdreamcatcher.restaurantChain.dto.request.ProductCreationRequestDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;

public interface ProductService {
    Iterable<ProductDTO> getAdminProducts() throws NoPermissionException, UserNotLoggedInException;

    ProductDTO getAdminProductById(Long id) throws UserNotLoggedInException, NoPermissionException;

    ProductDTO createProduct(ProductCreationRequestDTO productDTO) throws UserNotLoggedInException, NoPermissionException;
}
