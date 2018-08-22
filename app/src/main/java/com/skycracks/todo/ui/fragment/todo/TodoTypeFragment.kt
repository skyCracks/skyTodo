package com.skycracks.todo.ui.fragment.todo

import Constant
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.cxz.wanandroid.adapter.TodoPagerAdapter
import com.skycracks.todo.R
import com.skycracks.todo.base.BaseFragment
import com.skycracks.todo.core.bean.TodoTypeBean
import com.skycracks.todo.core.envent.TodoTypeEvent
import kotlinx.android.synthetic.main.fragment_todo_type.*
import org.greenrobot.eventbus.EventBus

class TodoTypeFragment : BaseFragment() , TabLayout.OnTabSelectedListener{

    private var isDone = false

    private lateinit var viewPagerAdapter: TodoPagerAdapter

    private lateinit var data : MutableList<TodoTypeBean>

    companion object {
        fun getInstance(done : Boolean): TodoTypeFragment {
            val fragment = TodoTypeFragment()
            val bundle = Bundle()
            bundle.putBoolean(Constant.TODO_DONE_KEY, done)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setLayoutId(): Int = R.layout.fragment_todo_type

    override fun initView() {
        isDone = arguments?.getBoolean(Constant.TODO_DONE_KEY) ?: false
        data = getData()
        viewPagerAdapter = TodoPagerAdapter(isDone,data, childFragmentManager)
        viewPager.run {
            adapter = viewPagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            offscreenPageLimit = data.size
        }
        tabLayout.run {
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(this@TodoTypeFragment)

        }
    }

    override fun lazyLoad() {

    }

    private fun getData(): MutableList<TodoTypeBean> {
        val list = mutableListOf<TodoTypeBean>()
        list.add(TodoTypeBean(Constant.TODO_TYPE_0, "只用这一个"))
        list.add(TodoTypeBean(Constant.TODO_TYPE_1, "工作"))
        list.add(TodoTypeBean(Constant.TODO_TYPE_2, "学习"))
        list.add(TodoTypeBean(Constant.TODO_TYPE_3, "生活"))
        return list
    }
    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        viewPager.currentItem = tab.position
        if(!isDone){
            EventBus.getDefault().post(TodoTypeEvent(tab.position))
        }
    }
}