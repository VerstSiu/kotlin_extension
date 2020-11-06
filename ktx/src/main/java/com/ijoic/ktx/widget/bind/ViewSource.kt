package com.ijoic.ktx.widget.bind

import androidx.annotation.IdRes
import android.view.View

/**
 * View source.
 *
 * @author verstsiu on 2018/9/19
 * @version 1.0
 */
interface ViewSource {
  /**
   * Returns related view instance or null.
   *
   * @param id view id.
   */
  fun<T: View> findViewById(@IdRes id: Int): T?
}