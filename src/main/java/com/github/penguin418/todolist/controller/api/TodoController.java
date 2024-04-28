package com.github.penguin418.todolist.controller.api;

import com.github.penguin418.todolist.model.dto.TodoDto;
import com.github.penguin418.todolist.model.request.TodoCreateRequest;
import com.github.penguin418.todolist.model.request.TodoStatePatchRequest;
import com.github.penguin418.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<TodoDto> createTodo(@RequestBody @Valid TodoCreateRequest todoCreateRequest){
        return ResponseEntity.ok(todoService.create(todoCreateRequest));
    }

    @RequestMapping(path = "", method = RequestMethod.PATCH)
    public ResponseEntity<TodoDto> patchState(@RequestBody @Valid TodoStatePatchRequest todoStatePatchRequest){
        return ResponseEntity.ok(todoService.patchState(todoStatePatchRequest));
    }

    @RequestMapping(path = "/recent", method = RequestMethod.GET)
    public ResponseEntity<TodoDto> getRecentTodo(){
        return ResponseEntity.ok(todoService.getRecent());
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<TodoDto>> getTodoList(){
        return ResponseEntity.ok(todoService.getList());
    }

    @RequestMapping(path = "", method = RequestMethod.GET, params = {"page"})
    public ResponseEntity<Page<TodoDto>> getTodoPage(Pageable pageable){
        return ResponseEntity.ok(todoService.getPage(pageable));
    }
}
