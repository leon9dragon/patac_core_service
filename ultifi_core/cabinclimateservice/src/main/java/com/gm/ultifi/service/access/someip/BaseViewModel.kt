/**
 * Copyright (c) 2023- ThunderSoft
 * All Rights Reserved by Thunder Software Technology Co., Ltd and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with ThunderSoft in writing by applicable law.
 *
 */
package com.gm.ultifi.service.access.someip

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

open class BaseViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(),
    LifecycleObserver {
    protected open var TAG: String = javaClass.simpleName
    var handler: Handler = Handler(Looper.getMainLooper())

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(null)
    }

    fun <T> getLiveData(key: String): MutableLiveData<T> {
        return savedStateHandle.getLiveData(key)
    }

    fun <T> setLiveData(key: String, t: T) {
        // notes: not use mSavedStateHandle.set(key, t) method, current thread change will causing exception
        savedStateHandle.getLiveData<Any>(key).postValue(t)
    }
}