package com.skycracks.todo.ui.activity.account

import Constant
import android.content.Intent
import android.view.View
import com.skycracks.todo.R
import com.skycracks.todo.base.MvpActivity
import com.skycracks.todo.core.bean.LoginBean
import com.skycracks.todo.core.envent.LoginEvent
import com.skycracks.todo.core.preference.Preference
import com.skycracks.todo.core.util.StatusBarUtil
import com.skycracks.todo.mvp.contract.account.LoginContract
import com.skycracks.todo.mvp.presenter.account.LoginPresenter
import com.skycracks.todo.ui.activity.MainActivity
import interval
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import toast

class LoginActivity : MvpActivity<LoginContract.View, LoginPresenter>(), LoginContract.View {

    private var rememberPassowrd : Boolean by Preference(Constant.REMEMBER_PASSWORD_KEY, true)

    private var username: String by Preference(Constant.USERNAME_KEY,"")

    private var password: String by Preference(Constant.PASSWORD_KEY,"")

    override fun createPresenter(): LoginPresenter = LoginPresenter()

    override fun setLayoutId(): Int = R.layout.activity_login

    override fun enableNetworkTip(): Boolean = false

    override fun initStatusBar() {
        StatusBarUtil.setTransparentForImageView(this@LoginActivity,loginLayout)
    }

    override fun initView() {
        if(rememberPassowrd){
            usernameEdit.setText(username)
            passwordEdit.setText(password)
        }
        login.setOnClickListener{
            it.interval {
                if (checkLogin()) {
                    mPresenter.loginWanAndroid(usernameEdit.text.toString(), passwordEdit.text.toString())
                }
            }
        }
        register.setOnClickListener{
            Intent(this, RegisterActivity::class.java).run {
                startActivity(this)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginEvent(event: LoginEvent) {
        Intent(this, MainActivity::class.java).run {
            startActivity(this)
            finish()
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    override fun loginSuccess(result: LoginBean) {
            isLogin = true
            username = result.username
            password = result.password
            toast(R.string.login_success)
            Intent(this, MainActivity::class.java).run {
                startActivity(this)
                finish()
            }
    }


    private fun checkLogin(): Boolean {
        usernameEdit.error = null
        passwordEdit.error = null
        var cancel = false
        var focusView: View? = null
        val username = usernameEdit.text.toString()
        val password = passwordEdit.text.toString()

        if (username.isEmpty()) {
            passwordEdit.error = getString(R.string.username_not_empty)
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
            passwordEdit.error = getString(R.string.password_length_short)
            focusView = passwordEdit
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