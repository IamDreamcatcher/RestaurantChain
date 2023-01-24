package com.iamdreamcatcher.restaurantChain.model.cook;

import com.iamdreamcatcher.restaurantChain.model.restaurant.Restaurant;
import com.iamdreamcatcher.restaurantChain.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cook")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;

    public Cook(User user, Restaurant restaurant, String number) {
        this.user = user;
        this.restaurant = restaurant;
        this.number = number;
    }

    private String number;
}
