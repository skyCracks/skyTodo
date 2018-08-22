package com.skycracks.todo.mvp.contract.todo

import com.skycracks.todo.base.IPresenter
import com.skycracks.todo.base.IView
import com.skycracks.todo.core.bean.TodoResponse

interface TodoContract {

    interface View : IView {

        fun showTodoList(todoResponse: TodoResponse)

        fun showDeleteSuccess()

        fun showUpdateSuccess()

    }

    interface Presenter : IPresenter<View> {

        fun getAllTodoList(type: Int)

        fun getNoTodoList(page: Int, type: Int)

        fun getDoneList(page: Int, type: Int)

        fun deleteTodoById(id: Int)

        fun updateTodoById(id: Int, status: Int)

    }
}