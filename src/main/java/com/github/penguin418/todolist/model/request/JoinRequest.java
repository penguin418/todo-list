package com.github.penguin418.todolist.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
}
