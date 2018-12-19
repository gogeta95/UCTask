package `in`.farmguide.myapplication.ui.base

import `in`.farmguide.farmerapp.central.util.SingleLiveEvent
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val errorLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    private val internalErrorLiveData: SingleLiveEvent<Pair<Int, String?>> = SingleLiveEvent()
    private val toastLiveData: SingleLiveEvent<Pair<Int, String?>> = SingleLiveEvent()
    private val successLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    private val blockingLoaderLiveData: MutableLiveData<Boolean> = MutableLiveData()


    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getErrorObservable() = errorLiveData

    fun getInternalErrorObservable() = internalErrorLiveData

    fun getToastObservable() = toastLiveData

    fun getSuccessObservable() = successLiveData

    fun getBlockingLoaderObservable() = blockingLoaderLiveData

    fun postError(messageResId: Int, extraText: String? = null) {
        internalErrorLiveData.postValue(Pair(messageResId, extraText))
    }

    fun postToast(messageResId: Int, extraText: String? = null) {
        toastLiveData.postValue(Pair(messageResId, extraText))
    }

    fun postSuccessToast(message: String?) {
        successLiveData.postValue(message)
    }

    fun postError(message: String?) {
        errorLiveData.postValue(message)
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun <T> Single<T>.applyLoader(): Single<T> =
        doOnSubscribe { setBlockingLoading(true) }
            .doFinally { setBlockingLoading(false) }

    private fun setBlockingLoading(isLoading: Boolean) {
        blockingLoaderLiveData.postValue(isLoading)
    }
}