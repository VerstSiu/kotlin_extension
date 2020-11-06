package com.ijoic.ktx.widget.bind

import android.app.Activity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Bind once.
 *
 * @param id view id.
 * @param <T> view type.
 */
fun<T: View> Fragment.bindOnce(@IdRes id: Int): ReadOnlyProperty<Fragment, T> = ViewProperty(id) {
  view
      .checkRootExist(this)
      .findViewById<T>(it)
      .checkCurrentExist(this, id)
}

/**
 * Bind once(optional).
 *
 * @param id view id.
 * @param <T> view type.
 */
fun<T: View> Fragment.bindOnceOptional(@IdRes id: Int): ReadOnlyProperty<Fragment, T?> = ViewProperty(id) {
  view
      .checkRootExist(this)
      .findViewById<T>(it)
}

/**
 * Bind once.
 *
 * @param id view id.
 * @param <T> view type.
 */
fun<T: View> Activity.bindOnce(@IdRes id: Int): ReadOnlyProperty<Activity, T> = ViewProperty(id) {
  findViewById<T>(it).checkCurrentExist(this, id)
}

/**
 * Bind once(optional).
 *
 * @param id view id.
 * @param <T> view type.
 */
fun<T: View> Activity.bindOnceOptional(@IdRes id: Int): ReadOnlyProperty<Activity, T?> = ViewProperty(id) {
  findViewById<T>(it)
}

/**
 * Bind once.
 *
 * @param id view id.
 * @param <T> view type.
 */
fun<T: View> RecyclerView.ViewHolder.bindOnce(@IdRes id: Int): ReadOnlyProperty<RecyclerView.ViewHolder, T> = ViewProperty(id) {
  itemView
      .checkRootExist(this)
      .findViewById<T>(it)
      .checkCurrentExist(this, id)
}

/**
 * Bind once(optional).
 *
 * @param id view id.
 * @param <T> view type.
 */
fun<T: View> RecyclerView.ViewHolder.bindOnceOptional(@IdRes id: Int): ReadOnlyProperty<RecyclerView.ViewHolder, T?> = ViewProperty(id) {
  itemView
      .checkRootExist(this)
      .findViewById<T>(it)
}

/**
 * Check root view exist.
 *
 * @param source root view provide source.
 */
private fun<T: View> T?.checkRootExist(source: Any): T {
  return this ?: throw IllegalStateException("could not find view, root view empty: source - $source")
}

/**
 * Check current view exist.
 *
 * @param source root view provide source.
 * @param id view id.
 */
private fun<T: View> T?.checkCurrentExist(source: Any, @IdRes id: Int): T {
  return this ?: throw IllegalStateException("could not find view: source - $source, id - $id")
}

/**
 * View property.
 *
 * @param id view id.
 * @param findView find related view instance.
 */
private class ViewProperty<R, T: View>(
    @IdRes private val id: Int,
    private val findView: (Int) -> T): ReadOnlyProperty<R, T> {

  private var view: T? = null

  override fun getValue(thisRef: R, property: KProperty<*>): T {
    return view ?: findView(id).apply { view = this }
  }
}