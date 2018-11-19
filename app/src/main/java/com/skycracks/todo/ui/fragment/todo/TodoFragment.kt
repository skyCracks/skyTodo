package com.skycracks.todo.ui.fragment.todo

import Constant
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.skycracks.todo.R
import com.skycracks.todo.base.MvpFragment
import com.skycracks.todo.core.bean.TodoDataBean
import com.skycracks.todo.core.bean.TodoResponse
import com.skycracks.todo.core.envent.RefreshTodoEvent
import com.skycracks.todo.core.envent.TodoEvent
import com.skycracks.todo.core.util.DialogUtil
import com.skycracks.todo.core.widget.SpaceItemDecoration
import com.skycracks.todo.core.widget.SwipeItemLayout
import com.skycracks.todo.mvp.contract.todo.TodoContract
import com.skycracks.todo.mvp.presenter.todo.TodoPresenter
import com.skycracks.todo.ui.activity.todo.AddTodoActivity
import com.skycracks.todo.ui.adapter.TodoAdapter
import interval
import kotlinx.android.synthetic.main.fragment_todo.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import toast

class TodoFragment : MvpFragment<TodoContract.View, TodoPresenter>(), TodoContract.View {


    private var isDone = false
    private var currentType = Constant.TODO_TYPE_0    // 当前类型

    private var currentPage = 1                // 当前页，从1开始
    private var isOver = true                  // 是否结束（没有下一页）
    private var isRefresh = true

    private var currentPosition = 0                // 当前点击item

    private val dataList = mutableListOf<TodoDataBean>()

    val todoAdapter: TodoAdapter by lazy {
        TodoAdapter(dataList)
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    companion object {
        fun getInstance(type: Int, done: Boolean): TodoFragment {
            val fragment = TodoFragment()
            val bundle = Bundle()
            bundle.putInt(Constant.TODO_TYPE_KEY, type)
            bundle.putBoolean(Constant.TODO_DONE_KEY, done)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createPresenter(): TodoPresenter = TodoPresenter()

    override fun setLayoutId(): Int = R.layout.fragment_todo

    override fun initView() {
        currentType = arguments?.getInt(Constant.TODO_TYPE_KEY) ?: Constant.TODO_TYPE_0
        isDone = arguments?.getBoolean(Constant.TODO_DONE_KEY) ?: false
        swipeRefresh.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }
        recyclerView.run {
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
            adapter = todoAdapter
            activity?.let {
                addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(it))
                }
            }

        todoAdapter.run {
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener(onLoadMoreListener, recyclerView)
            onItemChildClickListener = this@TodoFragment.onItemChildClickListener
            setEmptyView(R.layout.layout_empty, recyclerView)
        }
    }

    override fun lazyLoad() {
        if (isDone) {
            mPresenter?.getDoneList(currentPage, currentType)
        } else {
            mPresenter?.getNoTodoList(currentPage, currentType)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshTodoEvent(event: RefreshTodoEvent) {
        if (currentType == event.type) {
            refresh()
        }
    }

    override fun showLoading() {
        swipeRefresh?.isRefreshing = false
        if (isRefresh) {
            todoAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    override fun hideLoading() {
        swipeRefresh?.isRefreshing = false
        if (isRefresh) {
            todoAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    override fun showError(errorMsg: String) {
        todoAdapter.run {
            if (isRefresh)
                setEnableLoadMore(true)
            else
                loadMoreFail()
        }
        toast(errorMsg)
    }

    override fun showTodoList(todoResponse: TodoResponse) {
        val list = mutableListOf<TodoDataBean>()
        var bHeader = true
        todoResponse?.let {
            isOver = it.over
            if (!isOver) {
                currentPage++
            }
            it.datas.forEach { todoBean ->
                bHeader = true
                for (i in list.indices) {
                    if (todoBean.dateStr == list[i].header) {
                        bHeader = false
                        break
                    }
                }
                if (bHeader) {
                    list.add(TodoDataBean(true, todoBean.dateStr))
                }
                list.add(TodoDataBean(todoBean))
            }
            list.let {
                todoAdapter.run {
                    if (isRefresh) {
                        replaceData(it)
                    } else {
                        addData(it)
                    }
                    val size = it.size
                    if (size < todoResponse.size) {
                        loadMoreEnd(isRefresh)
                    } else {
                        loadMoreComplete()
                    }
                }
            }
        }
    }

    override fun showDeleteSuccess() {
        refresh()
        toast(getString(R.string.delete_success))
    }

    override fun showUpdateSuccess() {
        EventBus.getDefault().post(RefreshTodoEvent(currentType))
        toast(getString(if (isDone) R.string.undone else R.string.completed))
    }

    private fun refresh() {
        currentPage = 1
        isRefresh = true
        todoAdapter.setEnableLoadMore(false)
        lazyLoad()
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refresh()
    }


    private val onLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
        swipeRefresh.isRefreshing = false
        if (isOver) {
            todoAdapter.run {
                loadMoreEnd()
                setEnableLoadMore(false)
            }
        } else {
            lazyLoad()
        }
    }

    private val onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
                view.interval {
                    if (dataList.size != 0) {
                        currentPosition = position
                        val data = dataList[position].t
                        when (view.id) {
                            R.id.btn_delete -> {
                                activity?.let {
                                    DialogUtil.getConfirmDialog(it, getString(R.string.confirm_delete),
                                            DialogInterface.OnClickListener { _, _ ->
                                                mPresenter?.deleteTodoById(data.id)

                                            }).show()
                                }
                            }
                            R.id.btn_done -> {

                                mPresenter?.updateTodoById(data.id, if (isDone) Constant.TODO_STATUS_NO else Constant.TODO_STATUS_DONE)
                            }
                            R.id.item_todo_content -> {
                                val status: String = if (isDone) Constant.TODO_STATUS_SHOW else Constant.TODO_STATUS_UPDATE
                                EventBus.getDefault().postSticky(TodoEvent(currentType, status, data))
                                Intent(activity, AddTodoActivity::class.java).run {
                                    startActivity(this)
                                }
                            }
                        }
                    }
                }
            }

}