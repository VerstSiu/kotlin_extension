/*
 *
 *  Copyright(c) 2018 VerstSiu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
@file:kotlin.jvm.JvmMultifileClass
@file:kotlin.jvm.JvmName("ToastKt")

package com.ijoic.ktx.widget

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import com.ijoic.ktx.util.AppState

/**
 * Show toast.
 *
 * @param message message.
 * @param duration toast duration.
 */
fun Context?.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
  val context = this ?: AppState.getContext()

  if (context == null || message == null || message.isEmpty()) {
    return
  }
  Toast.makeText(context, message, duration).show()
}

/**
 * Show toast.
 *
 * @param messageRes message res.
 * @param duration toast duration.
 */
fun Context?.showToast(@StringRes messageRes: Int, duration: Int = Toast.LENGTH_SHORT) {
  val context = this ?: AppState.getContext()

  if (context == null || messageRes == 0) {
    return
  }
  context.showToast(context.getString(messageRes), duration)
}

/**
 * Show application toast.
 *
 * @param message message.
 * @param duration toast duration.
 */
fun Context?.showAppToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
  this?.applicationContext.showToast(message, duration)
}

/**
 * Show application toast.
 *
 * @param messageRes message res.
 * @param duration toast duration.
 */
fun Context?.showAppToast(@StringRes messageRes: Int, duration: Int = Toast.LENGTH_SHORT) {
  val context = this ?: AppState.getContext()

  if (context == null || messageRes == 0) {
    return
  }
  context.showAppToast(context.getString(messageRes), duration)
}