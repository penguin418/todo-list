package com.github.penguin418.todolist.config;

import com.github.penguin418.todolist.mapper.TodoUserMapper;
import com.github.penguin418.todolist.model.dto.TodoUserDto;
import com.github.penguin418.todolist.model.entity.TodoUserEntity;
import com.github.penguin418.todolist.model.request.WithdrawalRequest;
import com.github.penguin418.todolist.model.request.JoinRequest;
import com.github.penguin418.todolist.repository.TodoRepository;
import com.github.penguin418.todolist.repository.TodoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoUserDetailService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final TodoUserRepository todoUserRepository;
    private final TodoRepository todoRepository;
    private final TodoUserMapper todoUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return todoUserRepository.findByUsername(username)
                .map(todoUserMapper::toAuthentication)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    public TodoUserDto createAccount(JoinRequest joinRequest) {
        if (todoUserRepository.findByUsername(joinRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Duplicated username");
        }

        TodoUserEntity todoUser = new TodoUserEntity(
                null,
                joinRequest.getUsername(),
                passwordEncoder.encode(joinRequest.getPassword()),
                joinRequest.getNickname()
        );
        todoUserRepository.save(todoUser);
        return todoUserMapper.toDto(todoUser);
    }

    public TodoUserDto getTodoUserDto(TodoUser todoUser) {
        return todoUserMapper.toDto(todoUser);
    }

    @Transactional
    public void deleteAccount(TodoUser todoUser, WithdrawalRequest withdrawalRequest) {
        if (!passwordEncoder.matches(withdrawalRequest.getPassword(), todoUser.getPassword())) {
            throw new IllegalArgumentException("Password not matched");
        }
        todoRepository.deleteByTodoUserId(todoUser.getUserId());
        todoUserRepository.deleteById(todoUser.getUserId());
    }
}
