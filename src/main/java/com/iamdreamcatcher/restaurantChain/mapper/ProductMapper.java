package com.iamdreamcatcher.restaurantChain.mapper;

import com.iamdreamcatcher.restaurantChain.dto.model.ProductDTO;
import com.iamdreamcatcher.restaurantChain.model.product.Product;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    List<ProductDTO> toProductDTOList(List<Product> productList);

    ProductDTO toProductDTO(Product product);
}