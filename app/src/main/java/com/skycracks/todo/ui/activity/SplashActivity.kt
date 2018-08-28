package com.skycracks.todo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skycracks.todo.core.preference.Preference
import com.skycracks.todo.ui.activity.account.LoginActivity

class SplashActivity : AppCompatActivity() {

    protected var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(this@SplashActivity, if (isLogin)  MainActivity::class.java else LoginActivity::class.java).run{
            startActivity(this)
            finish()
        }
    }
}