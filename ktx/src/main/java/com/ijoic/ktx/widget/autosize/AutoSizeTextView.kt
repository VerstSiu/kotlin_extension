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
package com.ijoic.ktx.widget.autosize

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.ijoic.ktx.test.debug.DebugSource
import com.ijoic.ktx.test.debug.initDebugStatus
import com.ijoic.ktx.test.debug.printStateMessage

/**
 * Auto size text view.
 *
 * @author verstsiu on 2018/7/24.
 * @version 1.0
 */
class AutoSizeTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null): TextView(context, attrs), DebugSource {
  private var helper: AutoSizeTextHelper? = null
  override var debugEnabled = false

  init {
    initDebugStatus(context, attrs)
    helper = AutoSizeTextHelper(this).apply {
      loadFromAttributes(attrs, 0)
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    printStateMessage("measure") { "text: $text, width[${getSpecInfo(widthMeasureSpec)}], height[${getSpecInfo(heightMeasureSpec)}]" }
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    printStateMessage("measure") { "text: $text, measured width[$measuredWidth], measured height[$measuredHeight]" }
  }

  private fun getSpecInfo(spec: Int): String {
    val size = MeasureSpec.getSize(spec)
    val mode = MeasureSpec.getMode(spec)

    val modeText = when(mode) {
      MeasureSpec.AT_MOST -> "at_most"
      MeasureSpec.EXACTLY -> "exactly"
      MeasureSpec.UNSPECIFIED -> "unspecified"
      else -> "unknown"
    }
    return "spec: $size, mode: $modeText"
  }

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, left, top, right, bottom)
    printStateMessage("text") { "layout changed: changed - $changed, left - $left, top - $top, right - $right, bottom - $bottom" }
    helper?.onLayout(right - left, bottom - top)
  }

  override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
    super.onTextChanged(text, start, lengthBefore, lengthAfter)
    printStateMessage("text") { "text changed: text - $text, start - $start, length before - $lengthBefore, length after - $lengthAfter" }
    helper?.autoSizeText()
  }
}