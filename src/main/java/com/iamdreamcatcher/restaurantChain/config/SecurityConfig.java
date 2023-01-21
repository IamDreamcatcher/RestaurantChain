package com.iamdreamcatcher.restaurantChain.config;

import com.iamdreamcatcher.restaurantChain.model.user.Permission;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //To do: remove http basics, uncommitted form login and logout
        http.httpBasic()
                .and()
                .cors()
                .disable()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasAuthority(Permission.MANAGE.getPermission())
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated()

                //.and()
                //.formLogin()
                //.loginPage("/auth/login").permitAll()
                //.defaultSuccessUrl("/").permitAll()

                //.and()
                //.logout().permitAll()

                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied")

                .and()
                .authenticationProvider(authenticationProvider());

        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }
}
