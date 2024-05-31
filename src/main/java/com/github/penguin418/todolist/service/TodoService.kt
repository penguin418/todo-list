package com.github.penguin418.todolist.service

import com.github.penguin418.todolist.mapper.TodoMapper
import com.github.penguin418.todolist.model.constant.TodoState
import com.github.penguin418.todolist.model.constant.TodoState.Companion.isValidTransition
import com.github.penguin418.todolist.model.dto.TodoDto
import com.github.penguin418.todolist.model.entity.TodoEntity
import com.github.penguin418.todolist.model.request.TodoCreateRequest
import com.github.penguin418.todolist.model.request.TodoStatePatchRequest
import com.github.penguin418.todolist.repository.TodoRepository
import com.github.penguin418.todolist.repository.TodoUserRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
open class TodoService(
        private val todoUserRepository: TodoUserRepository?,
        private val todoRepository: TodoRepository?,
        private val todoMapper: TodoMapper?
) {
    @Transactional
    open fun create(todoCreateRequest: TodoCreateRequest): TodoDto {
        requireNotNull(todoCreateRequest.todoContent) { "Content is null" }
        val userEntity = todoUserRepository!!.findById(todoCreateRequest.userId).orElseThrow { IllegalArgumentException("User not found") }
        val entity = todoMapper!!.toEntity(todoCreateRequest)!!
        entity.todoState = TodoState.TODO
        entity.todoUser = userEntity
        todoRepository!!.save(entity)
        return todoMapper.toDto(entity)!!
    }

    @Transactional
    open fun patchState(todoStatePatchRequest: TodoStatePatchRequest): TodoDto? {
        val entity = todoRepository!!.findById(todoStatePatchRequest.todoId).orElseThrow { IllegalArgumentException("Todo not found") }
        require(isValidTransition(entity.todoState, todoStatePatchRequest.todoState!!)) { "Invalid state transition" }
        entity.todoState = todoStatePatchRequest.todoState
        return todoMapper!!.toDto(todoRepository.save(entity))
    }

    open val recent: TodoDto?
        get() {
            val pageable: Pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("createdDatetime")))
            val firstTodoPage = getPage(pageable)
            return firstTodoPage.get().findAny().orElse(null)
        }
    open val list: List<TodoDto>
        get() = todoMapper!!.toDtoList(todoRepository!!.findAll())

    open fun getPage(pageable: Pageable?): Page<TodoDto> {
        val entityPage = todoRepository!!.findAllPage(pageable!!)
        return entityPage.map { entity: TodoEntity? -> todoMapper!!.toDto(entity)!! }
    }
}
