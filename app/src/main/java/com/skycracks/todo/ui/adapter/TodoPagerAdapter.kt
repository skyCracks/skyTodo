package com.skycracks.todo.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import com.skycracks.todo.core.bean.TodoTypeBean
import com.skycracks.todo.ui.fragment.todo.TodoFragment


class TodoPagerAdapter(val done : Boolean,val list: List<TodoTypeBean>, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = mutableListOf<TodoFragment>()

    init {
        list.forEach {
            fragments.add(TodoFragment.getInstance(it.type,done))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? = list[position].name

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

}