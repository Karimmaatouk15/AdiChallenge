package com.example.adichallenge.network

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


abstract class CustomObserver<T> : Observer<T> {
    val onErrorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    var owner: LifecycleOwner? = null
        set(value) {
            owner?.let { onErrorLiveData.removeObservers(it) }
            value?.let {
                onErrorLiveData.observe(value, Observer { throwable ->
                    if (throwable != null) {
                        onError(throwable)
                    }
                })
                field = it
            }
        }

    abstract fun onError(t: Throwable)
}