package com.skycracks.todo.ui.activity

import Constant
import android.content.Intent
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.view.View
import com.skycracks.todo.R
import com.skycracks.todo.base.BaseActivity
import com.skycracks.todo.core.envent.TodoEvent
import com.skycracks.todo.core.envent.TodoTypeEvent
import com.skycracks.todo.core.widget.BottomNavigationViewHelper
import com.skycracks.todo.ui.activity.todo.AddTodoActivity
import com.skycracks.todo.ui.fragment.account.MineFragment
import com.skycracks.todo.ui.fragment.todo.TodoTypeFragment
import interval
import kotlinx.android.synthetic.main.activity_main.*

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import toast

class MainActivity : BaseActivity() {

    private var lastTime: Long = 0

    private var currentType = Constant.TODO_TYPE_0

    private var noTodoTypeFragment: TodoTypeFragment? = null
    private var doneTodoTypeFragment: TodoTypeFragment? = null
    private var mineFragment: MineFragment? = null

    private val fragmentManager by lazy {
        supportFragmentManager
    }

    override fun setLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        toolbar.run {
            toolbarTitle.text = getString(R.string.no_todo)
        }
        bottomNavigation.run {
            BottomNavigationViewHelper.disableShiftMode(this)
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            selectedItemId = R.id.action_no_todo
        }
        addTodoFloatButton.setOnClickListener {
            it.interval {
                EventBus.getDefault().postSticky(TodoEvent(currentType, Constant.TODO_STATUS_ADD, null))
                Intent(this@MainActivity, AddTodoActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doTodoEvent(event: TodoTypeEvent) {
        currentType = event.type
    }

    /**
     * NavigationItemSelect监听
     */
    private val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                showFragment(item.itemId)
                return@OnNavigationItemSelectedListener when (item.itemId) {
                    R.id.action_no_todo -> {
                        addTodoFloatButton.visibility = View.VISIBLE
                        true
                    }
                    R.id.action_done_todo -> {
                        addTodoFloatButton.visibility = View.GONE
                        true
                    }
                    R.id.action_mine -> {
                        addTodoFloatButton.visibility = View.GONE
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

    /**
     * 显示对应Fragment
     */
    private fun showFragment(index: Int) {
        fragmentManager.beginTransaction().apply {
            if (noTodoTypeFragment == null) {
                TodoTypeFragment.newInstance(false).let {
                    noTodoTypeFragment = it
                    add(R.id.fragmentContent, it)
                }
            }
            if (doneTodoTypeFragment == null) {
                TodoTypeFragment.newInstance(true).let {
                    doneTodoTypeFragment = it
                    add(R.id.fragmentContent, it)
                }
            }
            if (mineFragment == null) {
                MineFragment.newInstance().let {
                    mineFragment = it
                    add(R.id.fragmentContent, it)
                }
            }
            hideFragment(this)
            when (index) {
                R.id.action_no_todo -> {
                    toolbarTitle.text = getString(R.string.no_todo)
                    noTodoTypeFragment?.let {
                        show(it)
                    }
                }
                R.id.action_done_todo -> {
                    toolbarTitle.text = getString(R.string.done_todo)
                    doneTodoTypeFragment?.let {
                        show(it)
                    }
                }
                R.id.action_mine -> {
                    toolbarTitle.text = getString(R.string.mine)
                    mineFragment?.let {
                        show(it)
                    }
                }
            }
        }.commit()
    }

    /**
     * 隐藏所有fragment
     */
    private fun hideFragment(transaction: FragmentTransaction) {
        noTodoTypeFragment?.let {
            transaction.hide(it)
        }
        doneTodoTypeFragment?.let {
            transaction.hide(it)
        }
        mineFragment?.let {
            transaction.hide(it)
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(lastTime) <= 2000) {
                finish()
            } else {
                lastTime = System.currentTimeMillis()
                toast(getString(R.string.exit_tip))
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}