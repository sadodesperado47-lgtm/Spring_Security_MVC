package ru.spring.boot_security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class AdminController {

    private final UserService        userService;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder    passwordEncoder;

    @Autowired
    public AdminController(UserService userService,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userService        = userService;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder    = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String getUsers(Model model,
                           @AuthenticationPrincipal User currentUser) {
        model.addAttribute("users",       userService.getAllUsers());
        model.addAttribute("allRoles",    userRoleRepository.findAll());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newUser",     new User());
        model.addAttribute("activeTab",   "usersTable");
        return "admin";
    }

    @GetMapping("/admin/new")
    public String newUserForm(Model model,
                              @AuthenticationPrincipal User currentUser) {
        model.addAttribute("users",       userService.getAllUsers());
        model.addAttribute("allRoles",    userRoleRepository.findAll());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newUser",     new User());
        // ✅ Атрибут "user" нужен для th:object="${user}" в шаблоне new-user.html
        model.addAttribute("user",        new User());
        model.addAttribute("activeTab",   "newUser");
        return "admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String openEditModal(Model model,
                                @PathVariable Long id,
                                @AuthenticationPrincipal User currentUser) {
        model.addAttribute("users",       userService.getAllUsers());
        model.addAttribute("allRoles",    userRoleRepository.findAll());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newUser",     new User());
        model.addAttribute("editUser",    userService.getUserById(id));
        model.addAttribute("activeTab",   "usersTable");
        return "admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String openDeleteModal(Model model,
                                  @PathVariable Long id,
                                  @AuthenticationPrincipal User currentUser) {
        model.addAttribute("users",       userService.getAllUsers());
        model.addAttribute("allRoles",    userRoleRepository.findAll());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newUser",     new User());
        model.addAttribute("deleteUser",  userService.getUserById(id));
        model.addAttribute("activeTab",   "usersTable");
        return "admin";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("newUser") User user,
                          @RequestParam(value = "roleIds", required = false)
                          List<Long> roleIds) {
        if (roleIds != null) {
            Set<UserRole> roles = new HashSet<>();
            for (Long roleId : roleIds) {
                userRoleRepository.findById(roleId).ifPresent(roles::add);
            }
            user.setRoles(roles);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/edit")
    public String updateUser(@ModelAttribute("editUser") User user,
                             @RequestParam(value = "roleIds", required = false)
                             List<Long> roleIds) {
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