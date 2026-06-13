package ru.spring.boot_security.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.spring.boot_security.model.UserRole;
import ru.spring.boot_security.repository.UserRepository;
import ru.spring.boot_security.repository.UserRoleRepository;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        UserRole roleUser = userRoleRepository.findByUserName("ROLE_USER")
                .orElseGet(() -> userRoleRepository.save(new UserRole("ROLE_USER")));
        UserRole roleAdmin = userRoleRepository.findByUserName("ROLE_ADMIN")
                .orElseGet(() -> userRoleRepository.save(new UserRole("ROLE_ADMIN")));
    }

}
