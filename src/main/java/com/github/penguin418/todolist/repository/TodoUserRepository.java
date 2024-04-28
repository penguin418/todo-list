package com.github.penguin418.todolist.repository;

import com.github.penguin418.todolist.model.entity.TodoUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoUserRepository extends JpaRepository<TodoUserEntity, Long>{
    Optional<TodoUserEntity> findByUsername(String username);


}
