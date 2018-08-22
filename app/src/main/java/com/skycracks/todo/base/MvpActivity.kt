package com.skycracks.todo.base

import android.os.Bundle

abstract class MvpActivity<V : IView ,T : BasePresenter<V>> : BaseActivity(), IView {

    var mPresenter : T? = null
        private set


    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        mPresenter?.let {
            it.attachView(this as V)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.let {
            it.detachView()
        }
    }

    /**
     * 初始化Presenter
     *
     */
    protected abstract fun createPresenter() : T;
}