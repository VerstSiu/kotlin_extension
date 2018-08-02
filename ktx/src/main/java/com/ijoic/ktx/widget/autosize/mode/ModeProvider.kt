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
import com.ijoic.ktx.test.debug.DebugSource

/**
 * Size mode provider.
 *
 * @author verstsiu on 2018/7/31.
 * @version 1.0
 */
internal interface ModeProvider: DebugSource {
  /**
   * Layout callback.
   *
   * @param changed layout changed status.
   * @param width width.
   * @param height height.
   */
  fun onLayout(changed: Boolean, width: Int, height: Int)

  /**
   * Returns expected view width.
   *
   * @param measuredWidth measured width.
   */
  fun getExpectedViewWidth(measuredWidth: Int): Int

  /**
   * Returns expected view height.
   *
   * @param measuredHeight measured height.
   */
  fun getExpectedViewHeight(measuredHeight: Int): Int

  /**
   * Upgrade measure text.
   *
   * @param text measure text.
   */
  fun upgradeMeasureText(text: CharSequence) {}

  /**
   * Returns reset text size enabled status.
   *
   * @param onLayout on layout status.
   * @param text text.
   * @param paint text paint.
   * @param currTextSize current text size.
   * @param maxTextSize max text size.
   */
  fun isResetTextSizeEnabled(onLayout: Boolean, text: CharSequence, paint: TextPaint, currTextSize: Int, maxTextSize: Int): Boolean
}