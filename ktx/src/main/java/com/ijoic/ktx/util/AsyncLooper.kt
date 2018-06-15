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
package com.ijoic.ktx.util

import android.os.Handler
import android.os.Looper

/**
 * Asynchronous looper.
 *
 * @author verstsiu on 2018/6/15.
 * @version 1.0
 */
object AsyncLooper {
  /**
   * Main thread handler.
   *
   * @since 1.0
   */
  @JvmStatic
  val handler by lazy { Handler(Looper.getMainLooper()) }

  /**
   * Execute expected action at main thread.
   *
   * @param action action.
   * @since 1.0
   */
  @JvmStatic
  fun main(action: () -> Unit) {
    handler.post(action)
  }
}