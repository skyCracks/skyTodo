package com.skycracks.todo.mvp.contract.account

import com.skycracks.todo.base.IPresenter
import com.skycracks.todo.base.IView
import com.skycracks.todo.core.bean.LoginBean

interface LoginContract {

    interface View : IView {

        fun loginSuccess(result : LoginBean)

    }

    interface Presenter : IPresenter<View> {

        fun loginWanAndroid(username: String, password: String)

    }
}