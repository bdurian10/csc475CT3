package com.csc475.simpletodoapp

data class TodoItem(
    var id: Long = 0,
    var title: String,
    var isCompleted: Boolean = false
)
