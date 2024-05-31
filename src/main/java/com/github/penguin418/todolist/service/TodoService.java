package com.github.penguin418.todolist.service;

import com.github.penguin418.todolist.mapper.TodoMapper;
import com.github.penguin418.todolist.model.constant.TodoState;
import com.github.penguin418.todolist.model.dto.TodoDto;
import com.github.penguin418.todolist.model.entity.TodoEntity;
import com.github.penguin418.todolist.model.entity.TodoUserEntity;
import com.github.penguin418.todolist.model.request.TodoCreateRequest;
import com.github.penguin418.todolist.model.request.TodoStatePatchRequest;
import com.github.penguin418.todolist.repository.TodoRepository;
import com.github.penguin418.todolist.repository.TodoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final TodoUserRepository todoUserRepository;
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Transactional
    public TodoDto create(TodoCreateRequest todoCreateRequest) {
        if (todoCreateRequest.todoContent == null) throw new IllegalArgumentException("Content is null");
        TodoUserEntity userEntity = todoUserRepository.findById(todoCreateRequest.userId).orElseThrow(()->new IllegalArgumentException("User not found"));

        TodoEntity entity = todoMapper.toEntity(todoCreateRequest);
        entity.setTodoState(TodoState.TODO);
        entity.setTodoUser(userEntity);
        todoRepository.save(entity);
        return todoMapper.toDto(entity);
    }

    @Transactional
    public TodoDto patchState(TodoStatePatchRequest todoStatePatchRequest) {
        TodoEntity entity = todoRepository.findById(todoStatePatchRequest.todoId).orElseThrow(()->new IllegalArgumentException("Todo not found"));
        if (!TodoState.Companion.isValidTransition(entity.getTodoState(), todoStatePatchRequest.todoState)) {
            throw new IllegalArgumentException("Invalid state transition");
        }
        entity.setTodoState(todoStatePatchRequest.todoState);
        return todoMapper.toDto(todoRepository.save(entity));
    }

    public TodoDto getRecent() {
        Pageable pageable = PageRequest.of(0,1, Sort.by(desc("createdDatetime")));
        Page<TodoDto> firstTodoPage = getPage(pageable);
        return firstTodoPage.get().findAny().orElse(null);
    }

    public List<TodoDto> getList() {
        return todoMapper.toDtoList(todoRepository.findAll());

    }

    public Page<TodoDto> getPage(Pageable pageable) {
        Page<TodoEntity> entityPage = todoRepository.findAllPage(pageable);
        return entityPage.map(todoMapper::toDto);
    }
}
