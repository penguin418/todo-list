package com.github.penguin418.todolist.repository

import com.github.penguin418.todolist.model.entity.TodoEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<TodoEntity, Long> {
    @EntityGraph(attributePaths = ["todoUser"], type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT t FROM TodoEntity t LEFT JOIN t.todoUser")
    fun findAllPage(pageable: Pageable): Page<TodoEntity>
    @Query("SELECT t FROM TodoEntity t JOIN FETCH t.todoUser")
    override fun findAll(): List<TodoEntity>

    @Modifying
    @Query("DELETE FROM TodoEntity t WHERE t.todoUser.userId = :userId")
    fun deleteByTodoUserId(@Param("userId") userId: Long)
}
