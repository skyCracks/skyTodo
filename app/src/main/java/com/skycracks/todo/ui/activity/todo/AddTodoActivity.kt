package com.skycracks.todo.ui.activity.todo

import Constant
import android.app.DatePickerDialog
import android.view.View
import closeKeyBord
import com.skycracks.todo.R
import com.skycracks.todo.base.MvpActivity
import com.skycracks.todo.core.bean.TodoBean
import com.skycracks.todo.core.envent.RefreshTodoEvent
import com.skycracks.todo.core.envent.TodoEvent
import com.skycracks.todo.mvp.contract.todo.AddTodoContract
import com.skycracks.todo.mvp.presenter.todo.AddTodoPresenter
import formatCurrentDate
import kotlinx.android.synthetic.main.activity_add_todo.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import toast
import java.util.*

class AddTodoActivity : MvpActivity<AddTodoContract.View, AddTodoPresenter>(), AddTodoContract.View {

    /**
     * 类型
     */
    private var currentType = Constant.TODO_TYPE_0
    /**
     * 当前状态
     */
    private var currentStatus = Constant.TODO_STATUS_ADD

    private var mCurrentDate = formatCurrentDate()

    private var mTodoBean: TodoBean? = null

    override fun createPresenter(): AddTodoPresenter = AddTodoPresenter()

    override fun setLayoutId(): Int = R.layout.activity_add_todo

    override fun initView() {
        toolbar.run {
            when (currentStatus) {
                Constant.TODO_STATUS_ADD -> {
                    toolbarTitle.setText(R.string.todo_add)
                }
                Constant.TODO_STATUS_UPDATE -> {
                    toolbarTitle.setText(R.string.todo_update)
                }
                Constant.TODO_STATUS_SHOW -> {
                    toolbarTitle.setText(R.string.todo_show)
                }
            }
            toolbar.setNavigationOnClickListener {
                finish()
            }
        }
        dateLayout.setOnClickListener {
            closeKeyBord(contentEdit, this@AddTodoActivity)
            val now = Calendar.getInstance()
            val dpd = android.app.DatePickerDialog(this@AddTodoActivity,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        val currentMonth = month + 1
                        mCurrentDate = "$year-$currentMonth-$dayOfMonth"
                        dateText.text = mCurrentDate
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show()
        }
        saveButton.setOnClickListener {
            when (currentStatus) {
                Constant.TODO_STATUS_ADD -> {
                    val map = mutableMapOf<String, Any>()
                    map["type"] = currentType
                    map["title"] = titleEdit.text.toString()
                    map["content"] = contentEdit.text.toString()
                    map["date"] = mCurrentDate
                    mPresenter?.addTodo(map)
                }
                Constant.TODO_STATUS_UPDATE -> {
                    val map = mutableMapOf<String, Any>()
                    map["type"] = currentType
                    map["title"] = titleEdit.text.toString()
                    map["content"] = contentEdit.text.toString()
                    map["date"] = mCurrentDate
                    map["status"] = mTodoBean?.status ?: 0
                    mPresenter?.updateTodo(mTodoBean?.id ?: 0, map)
                }
            }
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    @Subscribe( sticky = true,threadMode = ThreadMode.MAIN)
    fun addTodoEvent(event: TodoEvent) {
        currentType = event.type
        currentStatus = event.status
        when (currentStatus) {
            Constant.TODO_STATUS_ADD -> {
                dateText.text = mCurrentDate
            }
            Constant.TODO_STATUS_UPDATE -> {
                mTodoBean = event.todoBean
                mTodoBean?.run {
                    titleEdit.setText(title)
                    contentEdit.setText(content)
                    dateText.text = dateStr
                }

            }
            Constant.TODO_STATUS_SHOW -> {
                mTodoBean = event.todoBean
                mTodoBean?.run {
                    titleEdit.setText(title)
                    contentEdit.setText(content)
                    dateText.text = dateStr
                    titleEdit.isEnabled = false
                    contentEdit.isEnabled = false
                    dateLayout.isEnabled = false
                    saveButton.visibility = View.GONE
                }
            }
        }
    }

    override fun showError(errorMsg: String) {
        toast(errorMsg)
    }

    override fun showAddTodoSuccess() {
        EventBus.getDefault().post(RefreshTodoEvent(currentType))
        finish()
    }

    override fun showUpdateTodoSuccess() {
        EventBus.getDefault().post(RefreshTodoEvent(currentType))
        finish()
    }
}