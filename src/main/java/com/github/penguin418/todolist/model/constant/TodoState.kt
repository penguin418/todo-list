package com.github.penguin418.todolist.model.constant

enum class TodoState {
    TODO,
    IN_PROGRESS,
    COMPLETED,
    PENDING;

    companion object {
        fun isValidTransition(oldState: TodoState?, newState: TodoState): Boolean {
            if (oldState == null) return true
            if (PENDING == oldState) return true
            return if (PENDING == newState) IN_PROGRESS == oldState else true
        }
    }
}
