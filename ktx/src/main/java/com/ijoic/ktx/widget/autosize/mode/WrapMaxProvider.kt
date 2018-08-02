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
package com.ijoic.ktx.widget.autosize.mode

import android.text.TextPaint
import com.ijoic.ktx.test.debug.printStateMessage
import kotlin.reflect.KMutableProperty0

/**
 * Wrap max provider.
 *
 * @author verstsiu on 2018/7/31.
 * @version 1.0
 */
internal class WrapMaxProvider: ModeProvider {
  override var debugEnabled = false

  private var maxLayoutWidth: Int? = null
  private var maxLayoutHeight: Int? = null

  private var measuredWidthChanged = false
  private var maxMeasuredWidth: Int? = null

  override fun onLayout(changed: Boolean, width: Int, height: Int) {
    updateHistorySize(this::maxLayoutWidth, width)
    updateHistorySize(this::maxLayoutHeight, height)

    measuredWidthChanged = false
  }

  private fun updateHistorySize(property: KMutableProperty0<Int?>, size: Int) {
    val oldSize = property.get()

    if (size > 0 && (oldSize == null || size > oldSize)) {
      property.set(size)
    }
  }

  override fun getExpectedViewWidth(measuredWidth: Int): Int {
    return Math.max(measuredWidth, maxLayoutWidth ?: 0)
  }

  override fun getExpectedViewHeight(measuredHeight: Int): Int {
    return Math.max(measuredHeight, maxLayoutHeight ?: 0)
  }

  override fun isResetTextSizeEnabled(onLayout: Boolean, text: CharSequence, paint: TextPaint, currTextSize: Int, maxTextSize: Int): Boolean {
    var widthChanged = measuredWidthChanged

    paint.textSize = maxTextSize.toFloat()
    val measuredWidth = Math.round(paint.measureText(text.toString()))
    val historyWidth = maxMeasuredWidth

    if (historyWidth == null || measuredWidth > historyWidth) {
      maxMeasuredWidth = measuredWidth
      measuredWidthChanged = currTextSize != maxTextSize
      widthChanged = true
      printStateMessage("upgrade max measured width") { "text - $text, history width - $historyWidth, measured width - $measuredWidth, changed - $widthChanged" }
    }
    if (onLayout && currTextSize == maxTextSize) {
      return false
    }
    return widthChanged
  }

}