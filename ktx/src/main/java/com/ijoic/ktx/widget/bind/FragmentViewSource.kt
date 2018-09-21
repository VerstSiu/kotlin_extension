package com.ijoic.ktx.widget.bind

import android.view.View

/**
 * Fragment view source.
 *
 * @author verstsiu on 2018/9/19
 * @version 1.0
 */
interface FragmentViewSource: ViewSource {
  /**
   * Returns root view instance.
   */
  fun getView(): View?

  override fun <T : View> findViewById(id: Int): T? {
    val view = this.getView() ?: throw IllegalStateException("could not find view, view root empty: source - $this")
    return view.findViewById(id)
  }
}