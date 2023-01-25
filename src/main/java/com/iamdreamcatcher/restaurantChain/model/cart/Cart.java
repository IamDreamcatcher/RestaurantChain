package com.iamdreamcatcher.restaurantChain.model.cart;

import com.iamdreamcatcher.restaurantChain.model.cartItem.CartItem;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus;
}
