package com.github.penguin418.todolist.controller.web

import com.github.penguin418.todolist.model.form.LoginForm
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("/auth")
class LoginController {
    @RequestMapping(value = ["/login"], method = [RequestMethod.GET])
    fun loginPage(@ModelAttribute("login") loginForm: LoginForm?): String {
        return "/login"
    }
}
