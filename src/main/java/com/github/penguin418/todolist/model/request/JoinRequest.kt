package com.github.penguin418.todolist.model.request

import jakarta.validation.constraints.NotNull

data class JoinRequest(
    @JvmField val username: @NotNull String? = null,
    @JvmField val password: @NotNull String? = null,
    @JvmField val nickname: @NotNull String? = null
)
