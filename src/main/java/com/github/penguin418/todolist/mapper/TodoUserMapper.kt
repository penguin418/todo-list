package com.github.penguin418.todolist.mapper

import com.github.penguin418.todolist.config.TodoUser
import com.github.penguin418.todolist.model.dto.TodoUserDto
import com.github.penguin418.todolist.model.entity.TodoUserEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface TodoUserMapper {
    fun toDto(entity: TodoUserEntity?): TodoUserDto?
    fun toDto(authentication: TodoUser?): TodoUserDto?
    fun toAuthentication(entity: TodoUserEntity?): TodoUser?
}
