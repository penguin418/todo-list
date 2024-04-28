package com.github.penguin418.todolist.model.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateRequest {
    private Long userId;
    private String todoContent;
}
