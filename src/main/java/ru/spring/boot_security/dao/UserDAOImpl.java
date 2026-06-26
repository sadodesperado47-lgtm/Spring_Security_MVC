package ru.spring.boot_security.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import ru.spring.boot_security.model.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public User findByUsername(String username) {
        try {
            User user = entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username",
                            User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            Hibernate.initialize(user.getRoles());
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

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
        User user = entityManager.find(User.class, id);
        if (user != null) {

            Hibernate.initialize(user.getRoles());
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = entityManager.createQuery("FROM User", User.class).getResultList();
        
        users.forEach(user -> Hibernate.initialize(user.getRoles()));
        return users;
    }
}