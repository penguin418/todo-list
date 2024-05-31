package com.github.penguin418.todolist.controller.api

import com.github.penguin418.todolist.config.TodoUser
import com.github.penguin418.todolist.config.TodoUserDetailService
import com.github.penguin418.todolist.model.dto.TodoUserDto
import com.github.penguin418.todolist.model.request.JoinRequest
import com.github.penguin418.todolist.model.request.WithdrawalRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val todoUserDetailService: TodoUserDetailService
) {
    @RequestMapping(path = ["/join"], method = [RequestMethod.POST])
    @ResponseStatus(code = HttpStatus.CREATED)
    fun signUp(@RequestBody joinRequest: @Valid JoinRequest): ResponseEntity<TodoUserDto> {
        return ResponseEntity.ok(todoUserDetailService.createAccount(joinRequest))
    }

    @RequestMapping(value = ["/me"], method = [RequestMethod.GET])
    fun me(@AuthenticationPrincipal todoUser: TodoUser): ResponseEntity<TodoUserDto> {
        return ResponseEntity.ok(todoUserDetailService.getTodoUserDto(todoUser))
    }

    @RequestMapping(path = ["/withdrawal"], method = [RequestMethod.POST])
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun delete(
            request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?,
            @AuthenticationPrincipal todoUser: TodoUser, @RequestBody withdrawalRequest: @Valid WithdrawalRequest): ResponseEntity<Void> {
        todoUserDetailService.deleteAccount(todoUser, withdrawalRequest)
        SecurityContextLogoutHandler().logout(request, response, authentication)
        return ResponseEntity.ok().build()
    }
}
