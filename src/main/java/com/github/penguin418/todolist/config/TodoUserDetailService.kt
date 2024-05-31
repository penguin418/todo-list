package com.github.penguin418.todolist.config

import com.github.penguin418.todolist.mapper.TodoUserMapper
import com.github.penguin418.todolist.model.dto.TodoUserDto
import com.github.penguin418.todolist.model.entity.TodoUserEntity
import com.github.penguin418.todolist.model.request.JoinRequest
import com.github.penguin418.todolist.model.request.WithdrawalRequest
import com.github.penguin418.todolist.repository.TodoRepository
import com.github.penguin418.todolist.repository.TodoUserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
open class TodoUserDetailService(
        val passwordEncoder: PasswordEncoder,
        val todoUserRepository: TodoUserRepository,
        val todoRepository: TodoRepository,
        val todoUserMapper: TodoUserMapper) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return todoUserRepository.findByUsername(username)
                .map { entity: TodoUserEntity? -> todoUserMapper.toAuthentication(entity) }
                .orElseThrow { UsernameNotFoundException(username) }
    }

    @Transactional
    open fun createAccount(joinRequest: JoinRequest): TodoUserDto {
        check(!todoUserRepository.findByUsername(joinRequest.username!!).isPresent) { "Duplicated username" }
        val todoUser = TodoUserEntity(
                null,
                joinRequest.username,
                passwordEncoder.encode(joinRequest.password),
                joinRequest.nickname
        )
        todoUserRepository.save(todoUser)
        return todoUserMapper.toDto(todoUser)
    }

    fun getTodoUserDto(todoUser: TodoUser?): TodoUserDto {
        return todoUserMapper.toDto(todoUser)
    }

    @Transactional
    open fun deleteAccount(todoUser: TodoUser, withdrawalRequest: WithdrawalRequest) {
        if (!passwordEncoder.matches(withdrawalRequest.password, todoUser.getPassword())) {
            throw BadCredentialsException("Password not matched")
        }
        todoRepository.deleteByTodoUserId(todoUser.userId)
        todoUserRepository.deleteById(todoUser.userId)
    }
}
