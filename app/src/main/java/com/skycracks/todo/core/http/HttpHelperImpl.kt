package com.skycracks.todo.core.http

import com.skycracks.todo.core.bean.AllTodoResponse
import com.skycracks.todo.core.bean.BaseResponse
import com.skycracks.todo.core.bean.LoginBean
import com.skycracks.todo.core.bean.TodoResponse
import com.skycracks.todo.core.http.api.AccountApi
import com.skycracks.todo.core.http.api.TodoApi
import kotlinx.coroutines.experimental.Deferred

object HttpHelperImpl : HttpHelper{

    private val accountApi: AccountApi = RetrofitHelper.getService(AccountApi::class.java)

    private val todoApi: TodoApi = RetrofitHelper.getService(TodoApi::class.java)

    override fun loginWanAndroid(username: String, password: String): Deferred<BaseResponse<LoginBean>> {
        return accountApi.loginWanAndroid(username,password)
    }

    override fun registerWanAndroid(username: String, password: String, repassowrd: String): Deferred<BaseResponse<LoginBean>> {
        return accountApi.registerWanAndroid(username,password,repassowrd)
    }

    override fun getAllTodoList(type: Int): Deferred<BaseResponse<AllTodoResponse>> {
       return todoApi.getAllTodoList(type)
    }

    override fun getNoTodoList(page: Int, type: Int): Deferred<BaseResponse<TodoResponse>> {
       return todoApi.getNoTodoList(page,type)
    }

    override fun getDoneList(page: Int, type: Int): Deferred<BaseResponse<TodoResponse>> {
        return todoApi.getDoneList(page,type)
    }

    override fun updateTodoById(id: Int, status: Int): Deferred<BaseResponse<Any>> {
        return todoApi.updateTodoById(id,status)
    }

    override fun deleteTodoById(id: Int): Deferred<BaseResponse<Any>> {
        return todoApi.deleteTodoById(id)
    }

    override fun addTodo(map: MutableMap<String, Any>): Deferred<BaseResponse<Any>> {
        return todoApi.addTodo(map)
    }

    override fun updateTodo(id: Int, map: MutableMap<String, Any>): Deferred<BaseResponse<Any>> {
        return todoApi.updateTodo(id,map)
    }
}