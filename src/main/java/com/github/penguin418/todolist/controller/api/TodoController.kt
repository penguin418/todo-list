package com.github.penguin418.todolist.controller.api

import com.github.penguin418.todolist.model.dto.TodoDto
import com.github.penguin418.todolist.model.request.TodoCreateRequest
import com.github.penguin418.todolist.model.request.TodoStatePatchRequest
import com.github.penguin418.todolist.service.TodoService
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/todo")
class TodoController(
    private val todoService: TodoService
) {
    @RequestMapping(path = [""], method = [RequestMethod.POST])
    @ResponseStatus(code = HttpStatus.CREATED)
    fun createTodo(@RequestBody todoCreateRequest: @Valid TodoCreateRequest): ResponseEntity<TodoDto> {
        return ResponseEntity.ok(todoService.create(todoCreateRequest))
    }

    @RequestMapping(path = [""], method = [RequestMethod.PATCH])
    fun patchState(@RequestBody todoStatePatchRequest: @Valid TodoStatePatchRequest): ResponseEntity<TodoDto> {
        return ResponseEntity.ok(todoService.patchState(todoStatePatchRequest))
    }

    @get:RequestMapping(path = ["/recent"], method = [RequestMethod.GET])
    val recentTodo: ResponseEntity<TodoDto>
        get() = ResponseEntity.ok(todoService.recent)

    @get:RequestMapping(path = ["/all"], method = [RequestMethod.GET])
    val todoList: ResponseEntity<List<TodoDto>>
        get() = ResponseEntity.ok(todoService.list)

    @RequestMapping(path = [""], method = [RequestMethod.GET], params = ["page"])
    fun getTodoPage(pageable: Pageable): ResponseEntity<Page<TodoDto>> {
        return ResponseEntity.ok(todoService.getPage(pageable))
    }
}
