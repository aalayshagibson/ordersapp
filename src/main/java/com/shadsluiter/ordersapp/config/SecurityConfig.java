package com.shadsluiter.ordersapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            // Disable CSRF for this course application
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // Allow registration and login
                .requestMatchers(
                    "/users/register",
                    "/users/registerForm",
                    "/users/login",
                    "/users/loginForm"
                ).permitAll()

                // Allow GET requests for the Orders API
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/orders/**"
                ).permitAll()

                // Allow the Orders pages
                .requestMatchers("/orders/**").permitAll()

                // Require authentication for everything else
                .anyRequest().authenticated()
            );

        return http.build();
    }

    /*
     * Password encoder used for storing/checking passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * AuthenticationManager required by UsersController
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
}