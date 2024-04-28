package com.github.penguin418.todolist.repository;

import com.github.penguin418.todolist.model.entity.TodoUserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TodoUserRepository extends CrudRepository<TodoUserEntity, Long> {
    Optional<TodoUserEntity> findByUsername(String username);


}
