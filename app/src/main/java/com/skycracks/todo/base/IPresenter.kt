package com.skycracks.todo.base

interface IPresenter<V : IView> {

    fun attachView(mView: V)

    fun detachView()

    fun cancelRequest()
}