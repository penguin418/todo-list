package com.github.penguin418.todolist.model.constant;

public enum TodoState {
    TODO,
    IN_PROGRESS,
    COMPLETED,
    PENDING;

    public static boolean isValidTransition(TodoState oldState, TodoState newState){
        if (oldState == null) return true;
        if (PENDING == oldState) return true;
        if (PENDING == newState) return IN_PROGRESS == oldState;
        return true;
    }
}
