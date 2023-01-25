package com.iamdreamcatcher.restaurantChain.model.cartItem;

import com.iamdreamcatcher.restaurantChain.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private Long amount;
}