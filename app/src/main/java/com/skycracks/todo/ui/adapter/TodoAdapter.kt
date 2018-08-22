package com.cxz.wanandroid.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.skycracks.todo.R
import com.skycracks.todo.core.bean.TodoBean
import com.skycracks.todo.core.bean.TodoDataBean

class TodoAdapter(data : List<TodoDataBean>) : BaseSectionQuickAdapter<TodoDataBean, BaseViewHolder>(R.layout.item_list_todo,R.layout.item_todo_header,data) {


    override fun convertHead(helper: BaseViewHolder?, item: TodoDataBean?) {
        helper ?: return
        item ?: return
        helper.setText(R.id.tv_header, item.header)
    }

    override fun convert(helper: BaseViewHolder?, item: TodoDataBean?) {
        helper ?: return
        item ?: return
        val itemData = item.t as TodoBean
        helper.setText(R.id.tv_todo_title, itemData.title)
                .addOnClickListener(R.id.btn_delete)
                .addOnClickListener(R.id.btn_done)
                .addOnClickListener(R.id.item_todo_content)
        val todoDescText = helper.getView<TextView>(R.id.tv_todo_desc)
        todoDescText.text = ""
        todoDescText.visibility = View.INVISIBLE
        if (itemData.content.isNotEmpty()) {
            todoDescText.visibility = View.VISIBLE
            todoDescText.text = itemData.content
        }
        val btn_done = helper.getView<Button>(R.id.btn_done)
        if (itemData.status == 0) {
            btn_done.text = mContext.resources.getString(R.string.mark_done)
        } else if (itemData.status == 1) {
            btn_done.text = mContext.resources.getString(R.string.restore)
        }
    }


}