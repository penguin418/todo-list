package com.github.penguin418.todolist.mapper;

import com.github.penguin418.todolist.config.TodoUser;
import com.github.penguin418.todolist.model.dto.TodoUserDto;
import com.github.penguin418.todolist.model.entity.TodoUserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoUserMapper {
    TodoUserDto toDto(TodoUserEntity entity);
    TodoUserDto toDto(TodoUser authentication);
    TodoUser toAuthentication(TodoUserEntity entity);
}
