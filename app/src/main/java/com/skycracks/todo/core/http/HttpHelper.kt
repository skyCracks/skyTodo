package com.skycracks.todo.core.http

import com.skycracks.todo.core.bean.AllTodoResponse
import com.skycracks.todo.core.bean.BaseResponse
import com.skycracks.todo.core.bean.LoginBean
import com.skycracks.todo.core.bean.TodoResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.FieldMap

interface HttpHelper {

    fun loginWanAndroid(username : String, password : String): Deferred<BaseResponse<LoginBean>>

    fun registerWanAndroid(username : String, password : String, repassowrd: String): Deferred<BaseResponse<LoginBean>>

    fun getAllTodoList(type: Int): Deferred<BaseResponse<AllTodoResponse>>

    fun getNoTodoList(page: Int, type: Int): Deferred<BaseResponse<TodoResponse>>

    fun getDoneList(page: Int, type: Int): Deferred<BaseResponse<TodoResponse>>

    fun updateTodoById(id: Int, status: Int): Deferred<BaseResponse<Any>>

    fun deleteTodoById(id: Int): Deferred<BaseResponse<Any>>

    fun addTodo(map: MutableMap<String, Any>): Deferred<BaseResponse<Any>>

    fun updateTodo(id: Int, @FieldMap map: MutableMap<String, Any>): Deferred<BaseResponse<Any>>
}