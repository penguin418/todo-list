package com.github.penguin418.todolist.model.request;

import com.github.penguin418.todolist.model.constant.TodoState;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoStatePatchRequest {
    private Long todoId;
    private TodoState todoState;
}
