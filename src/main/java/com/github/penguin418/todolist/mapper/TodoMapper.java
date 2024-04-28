package com.github.penguin418.todolist.mapper;

import com.github.penguin418.todolist.model.dto.TodoDto;
import com.github.penguin418.todolist.model.entity.TodoEntity;
import com.github.penguin418.todolist.model.request.TodoCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDatetime", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDatetime", ignore = true)
    @Mapping(target = "todoState", ignore = true)
    @Mapping(target = "todoId", ignore = true)
    TodoEntity toEntity(TodoCreateRequest todoCreateRequest);

    @Mapping(target = "userId", source = "todoUser.userId")
    @Mapping(target = "userNickname", source = "todoUser.nickname")
    TodoDto toDto(TodoEntity entity);

    List<TodoDto> toDtoList(List<TodoEntity> all);
}
