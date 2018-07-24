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

import android.view.View

/**
 * Auto size text info.
 *
 * @author verstsiu on 2018/7/24.
 * @version 1.0
 */
internal class AutoSizeTextInfo {

  private var atMostWidth: Int? = null
  private var maxExactlyWidth: Int? = null

  /**
   * Apply width measure spec.
   *
   * @param spec width measure spec.
   */
  internal fun applyWidthMeasureSpec(spec: Int) {
    val size = View.MeasureSpec.getSize(spec)
    val mode = View.MeasureSpec.getMode(spec)

    when(mode) {
      View.MeasureSpec.EXACTLY -> {
        val oldMaxExactlyWidth = maxExactlyWidth

        if (size > 0) {
          maxExactlyWidth = when (oldMaxExactlyWidth) {
            null -> size
            else -> Math.max(oldMaxExactlyWidth, size)
          }
        }
      }
      View.MeasureSpec.AT_MOST -> {
        if (size > 0) {
          atMostWidth = size
        }
      }
      View.MeasureSpec.UNSPECIFIED -> {
        // do nothing.
      }
    }
  }

  /**
   * Returns available width.
   */
  internal fun getAvailableWidth(): Int? {
    return maxExactlyWidth ?: atMostWidth
  }
}