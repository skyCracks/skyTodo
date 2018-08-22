package com.skycracks.todo.core.bean

data class AddTodoBean (
        val title: String,
        val content: String,
        val date: String,
        val type: Int
)