package com.github.penguin418.todolist.model.dto;

import com.github.penguin418.todolist.model.constant.TodoState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoDto {
    private Long todoId;
    private Long userId;
    private String userNickname;
    private String todoContent;
    private TodoState todoState;
    private Long createdBy;
    private LocalDateTime createdDatetime;
    private Long modifiedBy;
    private LocalDateTime modifiedDatetime;
}
