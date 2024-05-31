package com.github.penguin418.todolist.model.dto

import com.github.penguin418.todolist.model.constant.TodoState
import java.time.LocalDateTime

data class TodoDto (
    @JvmField val todoId: Long?,
    @JvmField val userId: Long?,
    @JvmField val userNickname: String?,
    @JvmField val todoContent: String?,
    @JvmField val todoState: TodoState?,
    @JvmField val createdBy: Long?,
    @JvmField val createdDatetime: LocalDateTime?,
    @JvmField val modifiedBy: Long?,
    @JvmField val modifiedDatetime: LocalDateTime?
)
