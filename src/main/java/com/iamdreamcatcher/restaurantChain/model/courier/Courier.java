package com.iamdreamcatcher.restaurantChain.model.courier;

import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courier")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;

    private String number;
}
