/*
 * Copyright (C) GM Global Technology Operations LLC 2022.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.patac.tester.util

import com.google.rpc.Code
import com.google.rpc.Status
import com.ultifi.core.common.util.StatusUtils

fun Status.extractCode(): Code {
    return StatusUtils.getCode(this, Code.UNKNOWN)
}