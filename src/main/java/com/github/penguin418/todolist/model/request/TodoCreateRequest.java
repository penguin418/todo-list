package com.github.penguin418.todolist.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateRequest {
    @NotNull
    private Long userId;
    @NotNull
    private String todoContent;
}
