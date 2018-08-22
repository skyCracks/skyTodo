package com.skycracks.todo.mvp.contract.todo

import com.skycracks.todo.base.IPresenter
import com.skycracks.todo.base.IView

interface AddTodoContract {


    interface View : IView {

        fun showAddTodoSuccess()

        fun showUpdateTodoSuccess()

    }

    interface Presenter : IPresenter<View> {

        fun addTodo(map : MutableMap<String, Any>)

        fun updateTodo(id: Int,map : MutableMap<String, Any>)

    }
}