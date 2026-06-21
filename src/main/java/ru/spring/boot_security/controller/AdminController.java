package ru.spring.boot_security.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.spring.boot_security.model.User;
import ru.spring.boot_security.model.UserRole;
import ru.spring.boot_security.repository.UserRoleRepository;
import ru.spring.boot_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", userRoleRepository.findAll());
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String getUserById(Model model, @PathVariable Long id) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("editUser", userService.getUserById(id));
        model.addAttribute("allRoles", userRoleRepository.findAll());
        return "users";
    }

    @PostMapping
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "roleIds", required = false)List<Long> roleIds) {
        if(roleIds != null) {
            Set<UserRole> roles = new HashSet<>();
            for(Long roleId: roleIds) {
                userRoleRepository.findById(roleId).ifPresent(roles::add);
            }
            user.setRoles(roles);
        }
        userService.addUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam(value = "roleIds", required = false)List<Long> roleIds) {
        if (roleIds != null) {
            Set<UserRole> roles = new HashSet<>();
            for (Long roleId : roleIds) {
                userRoleRepository.findById(roleId).ifPresent(roles::add);
            }
            user.setRoles(roles);
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }
}