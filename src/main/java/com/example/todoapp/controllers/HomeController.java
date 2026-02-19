package com.example.todoapp.controllers;

import com.example.todoapp.entities.User;
import com.example.todoapp.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(HttpSession session) {
        // Check if user is already logged in
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/todos";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        HttpSession session,
                        Model model) {
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("error", "Username cannot be empty");
            return "login";
        }

        // Find or create user
        User user = userService.findOrCreateUser(username.trim());

        // Set user in session
        session.setAttribute("currentUser", user.getId());
        session.setAttribute("username", user.getUsername());

        return "redirect:/todos";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
