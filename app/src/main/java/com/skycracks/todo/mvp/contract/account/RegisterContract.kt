package com.skycracks.todo.mvp.contract.account

import com.skycracks.todo.base.IPresenter
import com.skycracks.todo.base.IView
import com.skycracks.todo.core.bean.LoginBean

interface RegisterContract {

    interface View : IView{

        fun registerSuccess(result : LoginBean)

    }

    interface Presenter : IPresenter<View>{

        fun registerWanAndroid(username: String, password: String, repassowrd: String)

    }
}