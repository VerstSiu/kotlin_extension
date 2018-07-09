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
package app.ijoic.ktx.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import app.ijoic.ktx.R
import com.ijoic.ktx.widget.attr.*

/**
 * Ring view.
 *
 * @author verstsiu on 2018/7/9.
 * @version 1.0
 */
class RingView(context: Context, set: AttributeSet?): View(context, set) {

  private val config = AttrConfig().apply { init(context, set) }

  private val paint = Paint().apply {
    style = Paint.Style.STROKE
    isDither = true
    isAntiAlias = true
  }

  override fun onDraw(canvas: Canvas?) {
    canvas ?: return

    val centerX = width.toFloat() / 2F
    val centerY = height.toFloat() / 2F

    paint.strokeWidth = config.strokeWidth
    paint.color = config.strokeColor

    canvas.drawCircle(centerX, centerY, config.radius, paint)
  }

  /**
   * Attr config.
   */
  private class AttrConfig: StyleConfig() {
    /**
     * Radius.
     */
    val radius by bindDimen(R.attr.radius)

    /**
     * Stroke color.
     */
    val strokeColor by bindColor(R.attr.stroke_color)

    /**
     * Stroke width.
     */
    val strokeWidth by bindDimen(R.attr.stroke_width)
  }
}