package com.github.penguin418.todolist.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoUserDto {
    private Long userId;
    private String username;
    private String nickname;
    private LocalDateTime createdDatetime;
}
