package com.github.penguin418.todolist.repository;

import com.github.penguin418.todolist.model.entity.TodoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long>{

    @EntityGraph(attributePaths = {"todoUser"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT t FROM TodoEntity t LEFT JOIN t.todoUser")
    Page<TodoEntity> findAllPage(Pageable pageable);

    @Query("SELECT t FROM TodoEntity t JOIN FETCH t.todoUser")
    List<TodoEntity> findAll();

    @Modifying
    @Query("DELETE FROM TodoEntity t WHERE t.todoUser.userId = :userId")
    void deleteByTodoUserId(@Param("userId") Long userId);
}
