package com.github.penguin418.todolist.model.request

import com.github.penguin418.todolist.model.constant.TodoState

data class TodoStatePatchRequest (
    @JvmField val todoId: Long? = null,
    @JvmField val todoState: TodoState? = null
)
