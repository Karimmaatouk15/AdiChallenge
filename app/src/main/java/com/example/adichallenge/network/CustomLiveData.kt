package com.example.adichallenge.network

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class CustomLiveData<T> : MutableLiveData<T>() {
    private var observers: MutableList<CustomObserver<in T>> = arrayListOf()

    var error: Throwable? = null
        set(value) {
            val error = value ?: return
            observers.forEach {
                it.onErrorLiveData.postValue(error)
            }
        }

    fun observe(owner: LifecycleOwner, observer: CustomObserver<in T>) {
        super.observe(owner, observer)
        observer.owner = owner
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    override fun removeObserver(observer: Observer<in T>) {
        super.removeObserver(observer)
        if (observers.contains(observer as CustomObserver<in T>)) {
            observer.owner = null
            observers.remove(observer)
        }
    }
}