package com.github.penguin418.todolist.controller.web

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@Controller
@RequestMapping("/")
class DashboardController {
    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("isLogin", addIsLoginAttribute())
        return "index"
    }

    fun addIsLoginAttribute(): Boolean {
        return Optional.ofNullable(SecurityContextHolder.getContext().authentication)
                .map { auth: Authentication -> auth.isAuthenticated && auth !is AnonymousAuthenticationToken }
                .orElse(false)
    }
}
