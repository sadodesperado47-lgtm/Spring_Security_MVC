package ru.spring.boot_security.service;

import ru.spring.boot_security.dao.UserDAO;
import ru.spring.boot_security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {

        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        this.userDAO.addUser(user);

    }

    @Override
    @Transactional
    public void updateUser(User user) {

        this.userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public User removeUser(Long id) {
        return this.userDAO.removeUser(id);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {

        return this.userDAO.getUserById(id);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return this.userDAO.getAllUsers();
    }
    }
