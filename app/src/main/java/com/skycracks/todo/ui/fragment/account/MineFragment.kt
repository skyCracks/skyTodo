package com.skycracks.todo.ui.fragment.account

import Constant
import android.content.Intent
import com.skycracks.todo.R
import com.skycracks.todo.base.BaseFragment
import com.skycracks.todo.core.preference.Preference
import com.skycracks.todo.ui.activity.account.LoginActivity
import interval
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment() {

    private var username : String by Preference(Constant.USERNAME_KEY, "")

    private var rememberPassword : Boolean by Preference(Constant.REMEMBER_PASSWORD_KEY, true)

    override fun setLayoutId(): Int = R.layout.fragment_mine

    override fun initView() {
        accountText.text = username
        rememberPasswordCheckBox.isChecked = rememberPassword
        rememberPasswordCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            rememberPassword = isChecked
        }
        logoutText.setOnClickListener {
            it.interval {
                activity?.run {
                    isLogin = false
                    Intent(this, LoginActivity::class.java).run {
                        startActivity(this)
                        finish()
                    }
                }
            }
        }
    }

    override fun lazyLoad() {

    }
}