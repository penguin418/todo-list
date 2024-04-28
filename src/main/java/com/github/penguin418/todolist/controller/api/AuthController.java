package com.github.penguin418.todolist.controller.api;

import com.github.penguin418.todolist.config.TodoUser;
import com.github.penguin418.todolist.config.TodoUserDetailService;
import com.github.penguin418.todolist.model.request.WithdrawalRequest;
import com.github.penguin418.todolist.model.dto.TodoUserDto;
import com.github.penguin418.todolist.model.request.JoinRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TodoUserDetailService todoUserDetailService;

    @RequestMapping(path = "/join", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<TodoUserDto> signUp(@RequestBody JoinRequest joinRequest){
        return ResponseEntity.ok(todoUserDetailService.createAccount(joinRequest));
    }
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<TodoUserDto> me(@AuthenticationPrincipal TodoUser todoUser){
        return ResponseEntity.ok(todoUserDetailService.getTodoUserDto(todoUser));
    }
    @RequestMapping(path = "/withdrawal", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication,
            @AuthenticationPrincipal TodoUser todoUser, @RequestBody WithdrawalRequest withdrawalRequest){
        todoUserDetailService.deleteAccount(todoUser, withdrawalRequest);
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return ResponseEntity.ok().build();
    }
}
