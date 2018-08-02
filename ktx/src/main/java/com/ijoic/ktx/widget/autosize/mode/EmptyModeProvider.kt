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

/**
 * Empty mode provider.
 *
 * @author verstsiu on 2018/7/31.
 * @version 1.0
 */
object EmptyModeProvider: ModeProvider {
  override var debugEnabled = false

  override fun onLayout(changed: Boolean, width: Int, height: Int) {
    // do nothing.
  }

  override fun getExpectedViewWidth(measuredWidth: Int): Int {
    return measuredWidth
  }

  override fun getExpectedViewHeight(measuredHeight: Int): Int {
    return measuredHeight
  }

  override fun isResetTextSizeEnabled(onLayout: Boolean, text: CharSequence, paint: TextPaint, currTextSize: Int, maxTextSize: Int): Boolean {
    return false
  }
}