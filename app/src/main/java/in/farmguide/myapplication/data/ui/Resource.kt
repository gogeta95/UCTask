package `in`.farmguide.myapplication.data.ui

import android.support.annotation.StringRes

data class Resource<out T> constructor(val status: ResourceState, val data: T?, val message: String?, @StringRes val messageResId: Int?) {


    companion object {
        fun <T> Success(data: T): Resource<T> {
            return Resource(ResourceState.SUCCESS, data, null, null)
        }

        fun <T> Error(message: String?, data: T?): Resource<T> {
            return Resource(ResourceState.ERROR, data, message, null)
        }

        fun <T> Error(@StringRes messageResId: Int?): Resource<T> {
            return Resource(ResourceState.ERROR, null, null, messageResId)
        }

        fun <T> Error(): Resource<T> {
            return Resource(ResourceState.ERROR, null, null, null)
        }

        fun <T> Loading(): Resource<T> {
            return Resource(ResourceState.LOADING, null, null, null)
        }
    }

}