package com.github.penguin418.todolist.model.request

import jakarta.validation.constraints.NotNull


data class TodoCreateRequest(
    @JvmField val userId: @NotNull Long? = null,
    @JvmField val todoContent: @NotNull String? = null
)
