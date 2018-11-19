package com.skycracks.todo.mvp.presenter.todo

import cancelByActive
import com.skycracks.todo.base.BasePresenter
import com.skycracks.todo.core.bean.BaseResponse
import com.skycracks.todo.core.http.HttpHelperImpl
import com.skycracks.todo.mvp.contract.todo.AddTodoContract
import kotlinx.coroutines.Deferred
import responseTransform

open class AddTodoPresenter : BasePresenter<AddTodoContract.View>(), AddTodoContract.Presenter{

    private var addTodoAsync : Deferred<BaseResponse<Any>>? = null
    private var updateTodoAsync : Deferred<BaseResponse<Any>>? = null


    override fun cancelRequest() {
        addTodoAsync?.cancelByActive()
        updateTodoAsync?.cancelByActive()
    }

    override fun addTodo(map: MutableMap<String, Any>) {
        addTodoAsync?.cancelByActive()
        addTodoAsync = HttpHelperImpl.addTodo(map)
        obtainView()?.run {
            responseTransform(addTodoAsync){
              showAddTodoSuccess()
            }
        }
    }

    override fun updateTodo(id: Int, map: MutableMap<String, Any>) {
        updateTodoAsync?.cancelByActive()
        updateTodoAsync = HttpHelperImpl.updateTodo(id,map)
        obtainView()?.run {
            responseTransform(updateTodoAsync){
                showUpdateTodoSuccess()
            }
        }
    }
}