package com.skycracks.todo.core.bean

data class TodoResponse (
        val curPage: Int,
        val datas: MutableList<TodoBean>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
)