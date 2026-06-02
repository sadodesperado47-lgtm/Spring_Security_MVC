package ru.spring.boot_security.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.springbootcrud.model.User;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User removeUser(Long id) {
        User user = getUserById(id);
        if (user != null) {
            entityManager.remove(user);
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }
}

