package ru.spring.boot_security.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.spring.boot_security.model.User;
import ru.spring.boot_security.model.UserRole;
import ru.spring.boot_security.repository.UserRepository;
import ru.spring.boot_security.repository.UserRoleRepository;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {

        UserRole roleUser = userRoleRepository.findByName("ROLE_USER")
                .orElseGet(() -> userRoleRepository.save(new UserRole("ROLE_USER")));

        UserRole roleAdmin = userRoleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> userRoleRepository.save(new UserRole("ROLE_ADMIN")));


        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setName("Admin");
            admin.setLastname("Adminov");
            admin.setAge(40);
            admin.setEmail("admin@mail.ru");
            Set<UserRole> roles = new HashSet<>();
            roles.add(roleAdmin);
            roles.add(roleUser);
            admin.setRoles(roles);
            userRepository.save(admin);
        }


        if (userRepository.findByUsername("user") == null) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setName("User");
            user.setLastname("Userov");
            user.setAge(25);
            user.setEmail("user@mail.ru");
            Set<UserRole> roles = new HashSet<>();
            roles.add(roleUser);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}