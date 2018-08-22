package com.skycracks.todo.mvp.presenter.account

import Constant
import cancelByActive
import com.skycracks.todo.base.BasePresenter
import com.skycracks.todo.core.bean.BaseResponse
import com.skycracks.todo.core.bean.LoginBean
import com.skycracks.todo.core.http.HttpHelperImpl
import com.skycracks.todo.mvp.contract.account.LoginContract
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import loge
import responseTransform
import tryCatch

open class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter{

    private var loginAsync : Deferred<BaseResponse<LoginBean>>? = null

    override fun loginWanAndroid(username: String, password: String) {
        obtainView()?.run {
            loginAsync?.cancelByActive()
            loginAsync  = HttpHelperImpl.loginWanAndroid(username,password)
            responseTransform<LoginBean>(this,loginAsync ){
                loginSuccess(it)
            }
        }
    }

    override fun cancelRequest() {
        loginAsync?.cancelByActive()
    }

}