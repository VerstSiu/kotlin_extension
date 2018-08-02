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
 * Reset max provider.
 *
 * <p>WIP: This provider is not complete yet.</p>
 *
 * @author verstsiu on 2018/7/31.
 * @version 1.0
 */
internal class ResetMaxProvider: ModeProvider {
  override var debugEnabled = false

  override fun onLayout(changed: Boolean, width: Int, height: Int) {
  }

  override fun getExpectedViewWidth(measuredWidth: Int) = measuredWidth

  override fun getExpectedViewHeight(measuredHeight: Int) = measuredHeight

  override fun upgradeMeasureText(text: CharSequence) {
  }

  override fun isResetTextSizeEnabled(onLayout: Boolean, text: CharSequence, paint: TextPaint, currTextSize: Int, maxTextSize: Int): Boolean {
    if (onLayout) {
      return currTextSize < maxTextSize
    }
    return false
  }
}