/**
 * Copyright (c) 2023- ThunderSoft
 * All Rights Reserved by Thunder Software Technology Co., Ltd and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with ThunderSoft in writing by applicable law.
 *
 */
package com.gm.ultifi.base.someip

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleObserver

open class BaseViewModel : LifecycleObserver {
    protected open var TAG: String = javaClass.simpleName
    var handler: Handler = Handler(Looper.getMainLooper())
}