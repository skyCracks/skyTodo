package com.skycracks.todo.base

import android.os.Bundle

abstract class MvpFragment<V : IView ,T : BasePresenter<V>> : BaseFragment() , IView {

    val mPresenter by lazy { createPresenter() }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    /**
     * 初始化Presenter
     *
     */
    protected abstract fun createPresenter() : T;
}