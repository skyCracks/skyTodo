package com.skycracks.todo.core.bean

data class LoginBean(
        var id: Int,
        var username: String,
        var password: String,
        var icon: String?,
        var type: Int
        )