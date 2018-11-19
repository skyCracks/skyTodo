package com.skycracks.todo.ui.activity.account

import Constant
import android.view.View
import com.skycracks.todo.R
import com.skycracks.todo.base.MvpActivity
import com.skycracks.todo.core.bean.LoginBean
import com.skycracks.todo.core.envent.LoginEvent
import com.skycracks.todo.core.preference.Preference
import com.skycracks.todo.mvp.contract.account.RegisterContract
import com.skycracks.todo.mvp.presenter.account.RegisterPresenter
import interval
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.greenrobot.eventbus.EventBus
import toast

class RegisterActivity : MvpActivity<RegisterContract.View,RegisterPresenter>(),RegisterContract.View {

    private var username: String by Preference(Constant.USERNAME_KEY, "")

    private var password: String by Preference(Constant.PASSWORD_KEY, "")

    override fun createPresenter(): RegisterPresenter = RegisterPresenter()

    override fun setLayoutId(): Int = R.layout.activity_register

    override fun useEventBus(): Boolean = true

    override fun enableNetworkTip(): Boolean = false

    override fun initView() {
        toolbar.run {
            toolbarTitle.setText(R.string.register)
            setNavigationOnClickListener {
                finish()
            }
        }
        register.setOnClickListener {
            it.interval {
                if (checkRegister()) {
                    mPresenter.registerWanAndroid(usernameEdit.text.toString(), passwordEdit.text.toString(), repasswordEdit.text.toString())
                }
            }
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {
            toast(errorMsg)
    }

    override fun registerSuccess(result: LoginBean) {
            isLogin = true
            toast(R.string.login_success)
            username = result.username
            password = result.password
            EventBus.getDefault().post(LoginEvent())
            finish()
    }

    private fun checkRegister(): Boolean {
        usernameEdit.error = null
        passwordEdit.error = null
        repasswordEdit.error = null
        var cancel = false
        var focusView: View? = null
        val username = usernameEdit.text.toString()
        val password = passwordEdit.text.toString()
        val repassword = repasswordEdit.text.toString()
        if (username.isEmpty()) {
            usernameEdit.error = getString(R.string.username_not_empty)
            focusView = usernameEdit
            cancel = true
        }else if (username.length < 6) {
            usernameEdit.error = getString(R.string.username_length_short)
            focusView = usernameEdit
            cancel = true
        }

        if (password.isEmpty()) {
            passwordEdit.error = getString(R.string.password_not_empty)
            focusView = passwordEdit
            cancel = true
        } else if (password.length < 6) {
            repasswordEdit.error = getString(R.string.password_length_short)
            focusView = passwordEdit
            cancel = true
        }

        if (repassword.isEmpty()) {
            repasswordEdit.error = getString(R.string.repassword_not_empty)
            focusView = repasswordEdit
            cancel = true
        }else if(repassword.length != password.length){
            repasswordEdit.error = getString(R.string.repassword_diff)
            focusView = repasswordEdit
            cancel = true
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus()
            }
            return false
        } else {
            return true
        }
    }
}