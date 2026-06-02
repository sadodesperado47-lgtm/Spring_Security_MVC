package ru.spring.boot_security.service;


import org.example.springbootcrud.model.User;

import java.util.List;


public interface UserService {

    void addUser(User user);

    void updateUser(User user);

    User removeUser(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();
}