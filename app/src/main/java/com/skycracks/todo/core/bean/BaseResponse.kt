package com.skycracks.todo.core.bean

data class BaseResponse<T>(
        var errorCode: Int,
        var errorMsg: String,
        var data: T
)