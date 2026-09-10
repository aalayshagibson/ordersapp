package com.shadsluiter.ordersapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.shadsluiter.ordersapp.models.UserModel;
import com.shadsluiter.ordersapp.service.UserService;
import com.shadsluiter.ordersapp.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) {

        if (userService.findByLoginName(user.getLoginName()) != null) {
            return ResponseEntity.badRequest()
                    .body("User already exists!");
        }

        return ResponseEntity.ok(userService.save(user));
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @RequestBody UserModel loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getLoginName(),
                                loginRequest.getPassword()
                        )
                );

        String jwt = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(jwt);
       }
}