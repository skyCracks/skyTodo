package com.skycracks.todo.base

import java.lang.ref.WeakReference

abstract class BasePresenter<V : IView> : IPresenter<V>{

    var mView: WeakReference<V>? = null
        private set

    override fun attachView(mView : V) {
        this.mView = WeakReference(mView)
    }

    fun obtainView(): V? {
        return  mView?.get()
    }

    override fun detachView() {
        mView?.clear()
        mView = null
        cancelRequest()
    }

}