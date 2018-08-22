package com.skycracks.todo.core.bean

data class UpdateTodoBean (
        val title: String,
        val content: String,
        val date: String,
        val status: Int,
        val type: Int
)