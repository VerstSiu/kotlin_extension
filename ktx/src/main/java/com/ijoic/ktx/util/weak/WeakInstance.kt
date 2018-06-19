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
package com.ijoic.ktx.util.weak

import com.ijoic.ktx.util.orCreate
import java.lang.ref.WeakReference
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Returns weak instance delegate property.
 *
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T> weak(creator: () -> T): ReadOnlyProperty<R, T> {
  return weak(DefaultWeakHolder(), creator)
}

/**
 * Returns weak instance delegate property.
 *
 * @param holder weak holder.
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T> weak(holder: WeakHolder<T>, creator: () -> T) = object: ReadOnlyProperty<R, T> {

  override fun getValue(thisRef: R, property: KProperty<*>): T {
    val oldInstance = holder.get()

    return oldInstance.orCreate(creator) {
      holder.set(it)
    }
  }
}

/**
 * Returns weak instance delegate function property.
 *
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T, PARAM> weakFun(creator: (PARAM) -> T): ReadOnlyProperty<R, (PARAM) -> T> {
  return weakFun(DefaultWeakHolder(), creator)
}

/**
 * Returns weak instance delegate function property.
 *
 * @param holder weak holder.
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T, PARAM> weakFun(holder: WeakHolder<T>, creator: (PARAM) -> T) = object: ReadOnlyProperty<R, (PARAM) -> T> {

  override fun getValue(thisRef: R, property: KProperty<*>): (PARAM) -> T {
    return { param ->
      val oldInstance = holder.get()

      oldInstance.orCreate({ creator.invoke(param) }, { holder.set(it) })
    }
  }
}

/**
 * Returns weak instance delegate function property.
 *
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T, P1, P2> weakFun(creator: (P1, P2) -> T): ReadOnlyProperty<R, (P1, P2) -> T> {
  return weakFun(DefaultWeakHolder(), creator)
}

/**
 * Returns weak instance delegate property.
 *
 * @param holder weak holder.
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T, P1, P2> weakFun(holder: WeakHolder<T>, creator: (P1, P2) -> T) = object: ReadOnlyProperty<R, (P1, P2) -> T> {

  override fun getValue(thisRef: R, property: KProperty<*>): (P1, P2) -> T {
    return { p1, p2 ->
      val oldInstance = holder.get()

      oldInstance.orCreate({ creator.invoke(p1, p2) }, { holder.set(it) })
    }
  }
}

/**
 * Default weak holder.
 *
 * @author verstsiu on 2018/6/15.
 * @version 1.0
 */
private class DefaultWeakHolder<T>: WeakHolder<T> {

  private var refInstance: WeakReference<T>? = null

  override fun get() = refInstance?.get()

  override fun set(instance: T?) {
    refInstance = when (instance) {
      null -> null
      else -> WeakReference(instance)
    }
  }
}