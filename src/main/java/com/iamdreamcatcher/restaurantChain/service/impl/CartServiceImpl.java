package com.iamdreamcatcher.restaurantChain.service.impl;

import com.iamdreamcatcher.restaurantChain.dto.model.CartDTO;
import com.iamdreamcatcher.restaurantChain.dto.model.CartItemDTO;
import com.iamdreamcatcher.restaurantChain.exception.NoPermissionException;
import com.iamdreamcatcher.restaurantChain.exception.NotFoundException;
import com.iamdreamcatcher.restaurantChain.exception.UserNotLoggedInException;
import com.iamdreamcatcher.restaurantChain.mapper.CartMapper;
import com.iamdreamcatcher.restaurantChain.model.cart.Cart;
import com.iamdreamcatcher.restaurantChain.model.cart.CartStatus;
import com.iamdreamcatcher.restaurantChain.model.cartItem.CartItem;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.order.Order;
import com.iamdreamcatcher.restaurantChain.model.order.OrderStatus;
import com.iamdreamcatcher.restaurantChain.model.product.Product;
import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.repository.*;
import com.iamdreamcatcher.restaurantChain.service.CartService;
import com.iamdreamcatcher.restaurantChain.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ClientService clientService;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final OrderRepository orderRepository;
    @Override
    public void addProductToShoppingCart(Long id, Long pId, CartItemDTO cartItemDTO) throws NotFoundException, UserNotLoggedInException, NoPermissionException {
        Cart cart = getCart();
        if(cart == null) {
            cart = new Cart();
            cart.setCartStatus(CartStatus.ACTIVE);
            cart.setClient(clientService.getClient());
        }
        if (!cart.getCartItems().isEmpty() &&
                !cart.getCartItems().get(0).getProduct().getRestaurant().equals(getRestaurant(id))) {
            throw new NoPermissionException("Products from order must be from the same restaurant.");
        }
        for(int i = 0; i < cart.getCartItems().size(); i++) {
            CartItem cartItem = cart.getCartItems().get(i);
            if (cartItem.getProduct().getId().equals(cartItemDTO.getProductDTO().getId())) {
                cartItem.setAmount(cartItem.getAmount() + cartItemDTO.getAmount());
                cart.getCartItems().set(i, cartItem);
                cartItemDTO = null;
                break;
            }
        }
        if (cartItemDTO != null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(getProduct(pId, getRestaurant(id)));
            cartItem.setAmount(cartItemDTO.getAmount());
            cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);
        }

        cartRepository.save(cart);
    }



    @Override
    public void removeProductFromShoppingCart(Long id, Long pId, CartItemDTO cartItemDTO) throws NotFoundException, UserNotLoggedInException, NoPermissionException {
        Cart cart = getCart();

        for(int i = 0; i < cart.getCartItems().size(); i++) {
            CartItem cartItem = cart.getCartItems().get(i);
            if (cartItem.getProduct().getId().equals(cartItemDTO.getProductDTO().getId())) {
                if (cartItem.getAmount() - cartItemDTO.getAmount() < 0) {
                    throw new NotFoundException("Cart item in such amount not found");
                }
                cartItem.setAmount(cartItem.getAmount() - cartItemDTO.getAmount());
                if (cartItem.getAmount() == 0) {
                    cart.getCartItems().remove(i);
                } else {
                    cart.getCartItems().set(i, cartItem);
                }
                cartItemDTO = null;
                break;
            }
        }

        if (cartItemDTO != null) {
            throw new NotFoundException("Cart item not found");
        }

        cartRepository.save(cart);
    }

    @Override
    public CartDTO getShoppingCart() throws UserNotLoggedInException, NoPermissionException {
        return cartMapper.toCartDTO(getCart());
    }

    @Override
    public void clearShoppingCart() throws UserNotLoggedInException, NoPermissionException {
        Cart cart = getCart();
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public void checkout() throws UserNotLoggedInException, NotFoundException, NoPermissionException {
        Cart cart = getCart();
        if (cart.getCartItems().isEmpty()) {
            throw new NotFoundException("Cart is empty");
        }
        cart.setCartStatus(CartStatus.INACTIVE);
        Order order = new Order();
        order.setCart(cart);
        order.setOrderStatus(OrderStatus.FORMED);
        order.setPrice(getPriceOfCart(cart));

        Restaurant restaurant = cart.getCartItems().get(0).getProduct().getRestaurant();
        order.setRestaurant(restaurant);
        cartRepository.save(cart);
        orderRepository.save(order);
    }

    private Double getPriceOfCart(Cart cart) {
        Double price = (double) 0;
        for(CartItem cartItem: cart.getCartItems()) {
            price += cartItem.getProduct().getPrice();
        }

        return price;
    }

    private Cart getCart() throws UserNotLoggedInException, NoPermissionException {
        Client client = clientService.getClient();
        return cartRepository.findFirstCartByClientAndCartStatus(client, CartStatus.ACTIVE);
    }

    private Product getProduct(Long pId, Restaurant restaurant) throws NotFoundException {
        Product product = productRepository.findProductById(pId);
        if (!product.getRestaurant().equals(restaurant)) {
            throw new NotFoundException("This product doesn't belong to restaurant");
        }
        return product;
    }

    private Restaurant getRestaurant(Long id) throws NotFoundException {
        Restaurant restaurant = restaurantRepository.findRestaurantById(id);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant with such id not found");
        }
        return restaurant;
    }
}
