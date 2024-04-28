package com.github.penguin418.todolist.controller.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class DashboardController {
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("isLogin", addIsLoginAttribute());
        return "index";
    }

    public boolean addIsLoginAttribute() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(auth -> auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken))
                .orElse(false);
    }
}
