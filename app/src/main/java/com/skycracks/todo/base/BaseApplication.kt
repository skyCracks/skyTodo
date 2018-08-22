package com.skycracks.todo.base

import android.app.Application
import android.content.Context
import com.skycracks.todo.core.preference.Preference

class BaseApplication : Application() {

    companion object {

        lateinit var context: Context
            private set

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Preference.setContext(applicationContext)
    }

}