package com.skycracks.todo.core.bean

data class TodoListBean(
        val date: Long,
        val todoList: MutableList<TodoBean>
)