package com.github.penguin418.todolist.repository

import com.github.penguin418.todolist.model.entity.TodoUserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
@Repository
interface TodoUserRepository : CrudRepository<TodoUserEntity, Long> {
    fun findByUsername(username: String): Optional<TodoUserEntity>
}
