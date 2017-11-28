package co.omisego.omgshop.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View


/**
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 11/28/2017 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

abstract class BaseActivity<in V : BaseContract.BaseView, out P : BaseContract.BasePresenter<V>> : AppCompatActivity(), BaseContract.BaseView {
    protected abstract val mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun View.hideLoading() {
        visibility = View.GONE
    }

    fun log(message: String) {
        Log.d(this.javaClass.name, message)
    }
}