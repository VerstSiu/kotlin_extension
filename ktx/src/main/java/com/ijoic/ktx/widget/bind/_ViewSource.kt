package com.ijoic.ktx.widget.bind

import androidx.annotation.IdRes
import android.view.View
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Bind cache.
 */
private val bindCache = WeakHashMap<ViewSource, MutableList<BindProperty<ViewSource, *>>>()

/**
 * Returns cached property items.
 *
 * @param source view source.
 */
private fun getPropertyItems(source: ViewSource): MutableList<BindProperty<ViewSource, *>> {
  return getPropertyItemsOrNull(source) ?: mutableListOf<BindProperty<ViewSource, *>>().apply { bindCache[source] = this }
}

/**
 * Returns cached property items or null.
 *
 * @param source view source.
 */
private fun getPropertyItemsOrNull(source: ViewSource): MutableList<BindProperty<ViewSource, *>>? {
  return bindCache[source]
}

/**
 * Bind view.
 *
 * <p>This property will throw IllegalArgumentException when related view not found.</p>
 *
 * @param id view id.
 * @param <T> view type.
 */
fun<T: View> ViewSource.bindView(@IdRes id: Int): ReadOnlyProperty<ViewSource, T> {
  val result = BindProperty<ViewSource, T> { thisRef, _ ->
    thisRef.findViewById(id) ?: throw IllegalArgumentException("could not find related view: source - $thisRef, id - $id")
  }
  getPropertyItems(this).add(result)

  return result
}

/**
 * Bind view(optional).
 *
 * @param id view id.
 * @param <T> view type.
 */
fun<T: View> ViewSource.bindViewOptional(@IdRes id: Int): ReadOnlyProperty<ViewSource, T?> {
  val result = BindProperty<ViewSource, T?> { thisRef, _ ->
    thisRef.findViewById(id)
  }
  getPropertyItems(this).add(result)

  return result
}

/**
 * Release bind views.
 */
fun ViewSource.releaseBindViews() {
  getPropertyItemsOrNull(this)?.forEach { it.reset() }
}

/**
 * Bind property.
 *
 * @author verstsiu on 2018/9/19
 * @version 1.0
 */
internal class BindProperty<R, T>(private val fetchValue: (R, KProperty<*>) -> T): ReadOnlyProperty<R, T> {
  private var cacheValue: T? = null

  override fun getValue(thisRef: R, property: KProperty<*>): T {
    return cacheValue ?: fetchValue(thisRef, property).apply { cacheValue = this }
  }

  /**
   * Clear cache value.
   */
  fun reset() {
    cacheValue = null
  }

}