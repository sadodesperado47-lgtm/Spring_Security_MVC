package ru.spring.boot_security.controller;

import org.example.springbootcrud.model.User;
import org.example.springbootcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String getUserById(Model model, @PathVariable Long id) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("editUser", userService.getUserById(id));
        return "users";
    }

     @PostMapping
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return "redirect:/users";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }
}