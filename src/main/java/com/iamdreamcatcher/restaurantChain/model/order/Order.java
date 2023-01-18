package com.iamdreamcatcher.restaurantChain.model.order;

import com.iamdreamcatcher.restaurantChain.model.administrator.Administrator;
import com.iamdreamcatcher.restaurantChain.model.client.Client;
import com.iamdreamcatcher.restaurantChain.model.cook.Cook;
import com.iamdreamcatcher.restaurantChain.model.courier.Courier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ord")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    private Courier courier;
    @ManyToOne(fetch = FetchType.EAGER)
    private Cook cook;
    @ManyToOne(fetch = FetchType.EAGER)
    private Administrator administrator;
    private Double price;
}
