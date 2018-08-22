package com.skycracks.todo.mvp.presenter.todo

import cancelByActive
import com.skycracks.todo.base.BasePresenter
import com.skycracks.todo.core.bean.AllTodoResponse
import com.skycracks.todo.core.bean.BaseResponse
import com.skycracks.todo.core.bean.TodoResponse
import com.skycracks.todo.core.http.HttpHelperImpl
import com.skycracks.todo.mvp.contract.todo.TodoContract
import kotlinx.coroutines.experimental.Deferred
import responseTransform

open class TodoPresenter : BasePresenter<TodoContract.View>(), TodoContract.Presenter {

    private var getAllTodoListAsync: Deferred<BaseResponse<AllTodoResponse>>? = null
    private var getNoTodoListAsync: Deferred<BaseResponse<TodoResponse>>? = null
    private var getDoneListAsync: Deferred<BaseResponse<TodoResponse>>? = null
    private var deleteTodoByIdAsync: Deferred<BaseResponse<Any>>? = null
    private var updateTodoByIdAsync: Deferred<BaseResponse<Any>>? = null


    override fun cancelRequest() {
        getAllTodoListAsync?.cancelByActive()
        getNoTodoListAsync?.cancelByActive()
        getDoneListAsync?.cancelByActive()
        deleteTodoByIdAsync?.cancelByActive()
        updateTodoByIdAsync?.cancelByActive()
    }

    override fun getAllTodoList(type: Int) {
        getAllTodoListAsync?.cancelByActive()
        getAllTodoListAsync = HttpHelperImpl.getAllTodoList(type)
        obtainView()?.run {
            responseTransform<AllTodoResponse>(this, getAllTodoListAsync)
        }
    }

    override fun getNoTodoList(page: Int, type: Int) {
        if(page == 1){
            obtainView()?.showLoading()
        }
        getNoTodoListAsync?.cancelByActive()
        getNoTodoListAsync = HttpHelperImpl.getNoTodoList(page, type)
        obtainView()?.run {
            responseTransform<TodoResponse>(this, getNoTodoListAsync) {
                showTodoList(it)
            }
        }
    }

    override fun getDoneList(page: Int, type: Int) {
        getDoneListAsync?.cancelByActive()
        getDoneListAsync = HttpHelperImpl.getDoneList(page, type)
        obtainView()?.run {
            responseTransform<TodoResponse>(this, getDoneListAsync) {
                showTodoList(it)
            }
        }
    }

    override fun deleteTodoById(id: Int) {
        deleteTodoByIdAsync?.cancelByActive()
        deleteTodoByIdAsync = HttpHelperImpl.deleteTodoById(id)
        obtainView()?.run {
            responseTransform(this, deleteTodoByIdAsync) {
                showDeleteSuccess()
            }
        }
    }

    override fun updateTodoById(id: Int, status: Int) {
        updateTodoByIdAsync?.cancelByActive()
        updateTodoByIdAsync = HttpHelperImpl.updateTodoById(id, status)
        obtainView()?.run {
            responseTransform(this, updateTodoByIdAsync) {
                showUpdateSuccess()
            }
        }
    }
}