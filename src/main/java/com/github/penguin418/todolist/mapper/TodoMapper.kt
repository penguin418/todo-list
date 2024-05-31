package com.github.penguin418.todolist.mapper

import com.github.penguin418.todolist.model.dto.TodoDto
import com.github.penguin418.todolist.model.entity.TodoEntity
import com.github.penguin418.todolist.model.request.TodoCreateRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface TodoMapper {
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDatetime", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDatetime", ignore = true)
    @Mapping(target = "todoState", ignore = true)
    @Mapping(target = "todoId", ignore = true)
    fun toEntity(todoCreateRequest: TodoCreateRequest?): TodoEntity?

    @Mapping(target = "userId", source = "todoUser.userId")
    @Mapping(target = "userNickname", source = "todoUser.nickname")
    fun toDto(entity: TodoEntity?): TodoDto?
    fun toDtoList(all: List<TodoEntity?>?): List<TodoDto>
}
