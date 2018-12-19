package `in`.farmguide.myapplication.ui.base

import `in`.farmguide.myapplication.extensions.longToast
import `in`.farmguide.myapplication.extensions.showLoadingDialog
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import timber.log.Timber

abstract class BaseActivity<out VM : BaseViewModel> : AppCompatActivity() {

    private var mProgressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (hasInjector())
            AndroidInjection.inject(this)

        getViewModel().getErrorObservable().observe(this, Observer {
            longToast(it)
        })

        getViewModel().getInternalErrorObservable().observe(this, Observer {
            it?.let {
                val message = if (it.second == null) getString(it.first) else getString(it.first, it.second)
                longToast(message)
            }
        })

        getViewModel().getToastObservable().observe(this, Observer {
            it?.let {
                val message = if (it.second == null) getString(it.first) else getString(it.first, it.second)
                longToast(message)
            }
        })

        getViewModel().getSuccessObservable().observe(this, Observer {
            it?.let {
                longToast(it)
            }
        })

        getViewModel().getBlockingLoaderObservable().observe(this, Observer {
            it?.let {
                toggleLoaderVisibility(it)
            }
        })
    }

    fun toggleLoaderVisibility(visibility: Boolean) {
        if (visibility) showLoading() else hideLoading()
    }

    private fun showLoading() {
        Timber.d(" show loading")
        hideLoading()
        mProgressDialog = showLoadingDialog()
    }

    private fun hideLoading() {
        Timber.d(" hide loading")
        mProgressDialog?.let {
            if (it.isShowing)
                it.cancel()
        }
        mProgressDialog = null
    }

    open fun hasInjector() = true

    abstract fun getViewModel(): VM

}