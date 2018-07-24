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
package com.ijoic.ktx.test.debug

import android.content.Context
import android.util.AttributeSet
import com.ijoic.ktx.R

private val debugStyleAttrs = intArrayOf(R.attr.ktx_debugEnabled)

/**
 * Print state message.
 *
 * @param tag tag.
 * @param message message.
 */
internal fun DebugSource.printStateMessage(tag: String, getMessage: (() -> String)? = null) {
  if (!debugEnabled) {
    return
  }
  println(">>>> $tag >>>> ${getMessage?.invoke() ?: ""}")
}

/**
 * Initialize debug status according to xml attribute settings.
 *
 * @param context context.
 * @param attrs attributes.
 * @param defStyleAttr default style attributes.
 */
internal fun DebugSource.initDebugStatus(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) {
  val a = context.obtainStyledAttributes(attrs, debugStyleAttrs, defStyleAttr, 0) ?: return

  if (a.hasValue(0)) {
    debugEnabled = a.getBoolean(0, false)
  }
  a.recycle()
}