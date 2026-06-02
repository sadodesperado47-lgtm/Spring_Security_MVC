package ru.spring.boot_security.dao;


import ru.spring.boot_security.model.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);
    void updateUser(User user);
    User removeUser(Long id);
    User getUserById(Long id);
    List<User> getAllUsers();
}
