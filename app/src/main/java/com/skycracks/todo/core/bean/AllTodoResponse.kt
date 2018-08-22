package com.skycracks.todo.core.bean

data class AllTodoResponse (
        val type: Int,
        val doneList: MutableList<TodoListBean>,
        val todoList: MutableList<TodoListBean>
)