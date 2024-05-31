package com.github.penguin418.todolist.model.dto

import java.time.LocalDateTime


data class TodoUserDto (
    @JvmField val userId: Long?,
    @JvmField val username: String?,
    @JvmField val nickname: String?,
    @JvmField val createdDatetime: LocalDateTime?
)
