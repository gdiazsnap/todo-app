package com.example.todoapp.services;

import com.example.todoapp.entities.User;

import java.util.UUID;

public interface UserService {
    User findOrCreateUser(String username);

    User findByUsername(String username);

    User findById(UUID id);
}
