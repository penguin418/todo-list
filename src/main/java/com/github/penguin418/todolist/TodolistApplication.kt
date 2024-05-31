package com.github.penguin418.todolist

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class TodolistApplication
fun main(args: Array<String>) {
    runApplication<TodolistApplication>(*args)
}