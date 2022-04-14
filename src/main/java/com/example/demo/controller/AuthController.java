package com.example.demo.controller;

import com.example.demo.security.Token;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final TokenProvider provider;
    private final UserService service;

    public AuthController(TokenProvider provider, UserService service) {
        this.provider = provider;
        this.service = service;
    }

    @PostMapping("/login")
    public Token authentication(@RequestBody AuthenticationRequest request) {
        return provider.getTokenForUser(service.gerUserByName(request.name));
    }

    @PostMapping("/resolve/token")
    public String resolveToken(@RequestBody Token token) {
        return "You are - " + provider.getUserFromToken(token).getName();
    }

    @Value
    public static class AuthenticationRequest {
        String name;
        String password;
    }
}
