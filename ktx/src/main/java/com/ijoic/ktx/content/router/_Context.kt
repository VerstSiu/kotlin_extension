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
package com.ijoic.ktx.content.router

import android.content.Context
import android.content.Intent

/**
 * Route to expected activity.
 *
 * @param action action.
 * @param onError error action.
 */
fun Context.routeTo(action: String, onError: ((Throwable) -> Unit)? = null) {
  routeTo(Intent(action), onError)
}

/**
 * Route to expected activity.
 *
 * @param intent intent.
 * @param onError error action.
 */
fun Context.routeTo(intent: Intent, onError: ((Throwable) -> Unit)? = null) {
  try {
    startActivity(intent)
  } catch (e: Throwable) {
    e.printStackTrace()

    val errorCallback = onError ?: Router.onRouteError
    errorCallback.invoke(e)
  }
}