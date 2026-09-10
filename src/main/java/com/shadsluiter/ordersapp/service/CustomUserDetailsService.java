package com.shadsluiter.ordersapp.service;

import com.shadsluiter.ordersapp.data.UserRepository;
import com.shadsluiter.ordersapp.models.UserEntity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginName)
            throws UsernameNotFoundException {

        UserEntity user = userRepository.findByLoginName(loginName);

        if (user == null) {
            throw new UsernameNotFoundException(
                    "User not found: " + loginName
            );
        }

        return User.builder()
                .username(user.getLoginName())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}