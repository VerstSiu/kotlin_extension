package com.ijoic.ktx.widget.attr

import android.content.Context
import android.util.AttributeSet

/**
 * Style config.
 *
 * @author verstsiu on 2018/7/7.
 * @version 1.0
 */
abstract class StyleConfig(
    private val styleAttr: Int = 0,
    private val styleRes: Int = 0) {

  internal val attrItems = mutableListOf<AttrItem>()

  /**
   * Initialize.
   *
   * @param context context.
   * @param set attribute set.
   */
  fun init(context: Context, set: AttributeSet? = null) {
    val items = attrItems.apply { sortBy { it.getAttribute() } }
    val attrs = items.map { it.getAttribute() }.toIntArray()
    val array = context.obtainStyledAttributes(set, attrs, styleAttr, styleRes) ?: return

    items.forEachIndexed { index, it ->
      it.applyInit(array, index)
    }
    array.recycle()
  }
}