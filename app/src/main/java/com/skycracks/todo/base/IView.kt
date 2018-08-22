package com.skycracks.todo.base

interface IView{

    fun showLoading()

    fun hideLoading()

    fun showError(errorMsg : String)

}