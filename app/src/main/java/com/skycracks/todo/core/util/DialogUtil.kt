package com.skycracks.todo.core.util

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

object DialogUtil {

    fun getDialog(context: Context): AlertDialog.Builder {
        return AlertDialog.Builder(context)
    }

    fun getConfirmDialog(context: Context, message: String,
                         onClickListener: DialogInterface.OnClickListener): AlertDialog.Builder {
        val builder = getDialog(context)
        builder.setMessage(message)
        builder.setPositiveButton("确定", onClickListener)
        builder.setNegativeButton("取消", null)
        return builder
    }
}