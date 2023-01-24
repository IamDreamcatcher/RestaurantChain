package com.iamdreamcatcher.restaurantChain.model.owner;

import com.iamdreamcatcher.restaurantChain.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "owner")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    //TO DO: ad photos;byte[] photo;
    private String cooperationEmail;
}
