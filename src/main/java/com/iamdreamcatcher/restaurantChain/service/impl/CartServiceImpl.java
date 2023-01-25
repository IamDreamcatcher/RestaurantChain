package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.CartItemDTO;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.model.cart.Cart;
import com.iamdreamcatcher.restaurantChain.model.cart.CartStatus;
import com.iamdreamcatcher.restaurantChain.model.cartItem.CartItem;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.product.Product;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import com.iamdreamcatcher.restaurantChain.repository.*;
import com.iamdreamcatcher.restaurantChain.security.AuthContextHandler;
import com.iamdreamcatcher.restaurantChain.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ClientRepository clientRepository;
    private final AuthContextHandler authContextHandler;
    private final CartItemRepository cartItemRepository;
    @Override
    public void addProductToShoppingCart(Long id, Long pId, CartItemDTO cartItemDTO) throws NotFoundException, UserNotLoggedInException {
        Restaurant restaurant = restaurantRepository.findRestaurantById(id);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant with such id not found");
        }
        Product product = productRepository.findProductById(pId);
        if (product.getRestaurant().getId() != restaurant.getId()) {
            throw new NotFoundException("This product doesn't belong to restaurant");
        }
        User user = authContextHandler.getLoggedInUser();
        Client client = clientRepository.findByUser(user);
        if (client == null) {
            throw new NotFoundException("User is not a client");
        }
        Cart cart = cartRepository.findFirstCartByClientAndCartStatus(client, CartStatus.ACTIVE);
        if(cart == null) {
            cart = new Cart();
            cart.setCartStatus(CartStatus.ACTIVE);
            cart.setClient(client);
        }

        for(int i = 0; i < cart.getCartItems().size(); i++) {
            CartItem cartItem = cart.getCartItems().get(i);
            if (cartItem.getProduct().getId() == cartItemDTO.getProductDTO().getId()) {
                cartItem.setAmount(cartItem.getAmount() + cartItemDTO.getAmount());
                cart.getCartItems().set(i, cartItem);
                cartItemDTO = null;
                break;
            }
        }
        if (cartItemDTO != null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setAmount(cartItemDTO.getAmount());
            cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);
        }

        cartRepository.save(cart);
    }
}
