package com.example.demo.service;

import com.example.demo.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User gerUserByName(String name) {
        return new User(1L,"some name", "pass", "passHash");
    }
}
