/**
 * Copyright (c) 2023- ThunderSoft
 * All Rights Reserved by Thunder Software Technology Co., Ltd and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with ThunderSoft in writing by applicable law.
 *
 */
package com.gm.ultifi.service.access.someip

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.gm.ultifi.service.AccessService
import ts.car.someip.sdk.SomeIpCallback
import ts.car.someip.sdk.SomeIpClientProxy
import ts.car.someip.sdk.common.ResultValue
import ts.car.someip.sdk.common.SomeIpData


abstract class BaseAppViewModel(stateHandle: SavedStateHandle) : BaseViewModel(stateHandle) {
    var isReady = false
    var isServerAvailable = false


    var someIpClientProxy: SomeIpClientProxy? = null
    var someIpCallback: SomeIpCallback = object : SomeIpCallback {
        override fun onSomeIpEvent(msg: SomeIpData) {
            doOnSomeIpEvent(msg)
        }

        override fun onChangeServiceStatus(isReady: Boolean) {
            Log.i(TAG, "onChangeServiceStatus = $isReady")
            if (isReady) {
                startClient(isReady = true)
            } else {
                doStopClient()
            }

        }

        override fun onRequest(data: SomeIpData): SomeIpData? {
            return doOnRequest(data)
        }
    }

    init {
        initSomeIp()
    }

    open fun onChangeServiceStatus(isReady: Boolean) {

    }

    private fun initSomeIp() {
        someIpClientProxy = SomeIpClientProxy.getInstance(AccessService.context)
        someIpClientProxy?.registerCallback(someIpCallback)
        someIpClientProxy?.isServiceReady?.let { startClient(it) }
    }

    fun startClient(isReady: Boolean) {
        Log.i(TAG, "startClient:$isReady")
        this.isReady = isReady
        if (!isReady) {
            someIpClientProxy?.reBindService()
            return
        }
        val ret = doStartClient()
        Log.i(TAG, "startClient:$ret")
        if (ret == ResultValue.OK || ret == ResultValue.ALREADY_STARTED) {
            doClientOk()
        }
    }

    protected abstract fun doOnRequest(data: SomeIpData): SomeIpData?

    protected abstract fun doOnSomeIpEvent(data: SomeIpData)

    protected abstract fun doClientOk()

    protected abstract fun doStartClient(): Int?

    protected abstract fun doStopClient()

    override fun onCleared() {
        if (someIpClientProxy != null) {
            someIpClientProxy?.unregisterCallback(someIpCallback)
            doStopClient()
            someIpClientProxy?.unBindService()
        }
        super.onCleared()
    }

    fun startClient(clientId: Long): Int? {
        return someIpClientProxy?.startClient(clientId)
    }

    fun stopClient(clientId: Long) {
        someIpClientProxy?.stopClient(clientId)
    }
}