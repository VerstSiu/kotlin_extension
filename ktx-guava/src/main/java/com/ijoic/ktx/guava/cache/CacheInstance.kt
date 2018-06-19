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
package com.ijoic.ktx.guava.cache

import com.google.common.cache.CacheBuilder
import com.ijoic.ktx.util.weak.WeakHolder
import com.ijoic.ktx.util.weak.weak
import com.ijoic.ktx.util.weak.weakFun
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import kotlin.properties.ReadOnlyProperty

private const val DEFAULT_HOLD_DURATION = 2000L
private val DEFAULT_HOLD_UNIT = TimeUnit.MICROSECONDS

/**
 * Returns cache instance delegate property.
 *
 * @param duration instance hold duration.
 * @param unit instance hold unit.
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T> cache(duration: Long = DEFAULT_HOLD_DURATION, unit: TimeUnit = DEFAULT_HOLD_UNIT, creator: () -> T): ReadOnlyProperty<R, T> {
  return weak(CacheHolder(duration, unit), creator)
}

/**
 * Returns cache instance delegate function property.
 *
 * @param duration instance hold duration.
 * @param unit instance hold unit.
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T, PARAM> cacheFun(duration: Long = DEFAULT_HOLD_DURATION, unit: TimeUnit = DEFAULT_HOLD_UNIT, creator: (PARAM) -> T): ReadOnlyProperty<R, (PARAM) -> T> {
  return weakFun(CacheHolder(duration, unit), creator)
}

/**
 * Returns cache instance delegate function property.
 *
 * @param duration instance hold duration.
 * @param unit instance hold unit.
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T, P1, P2> cacheFun(duration: Long = DEFAULT_HOLD_DURATION, unit: TimeUnit = DEFAULT_HOLD_UNIT, creator: (P1, P2) -> T): ReadOnlyProperty<R, (P1, P2) -> T> {
  return weakFun(CacheHolder(duration, unit), creator)
}

/**
 * Returns weak cache instance delegate property.
 *
 * @param duration instance hold duration.
 * @param unit instance hold unit.
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T> weakCache(duration: Long = DEFAULT_HOLD_DURATION, unit: TimeUnit = DEFAULT_HOLD_UNIT, creator: () -> T): ReadOnlyProperty<R, T> {
  return weak(WeakCacheHolder(duration, unit), creator)
}

/**
 * Returns weak cache instance delegate function property.
 *
 * @param duration instance hold duration.
 * @param unit instance hold unit.
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T, PARAM> weakCacheFun(duration: Long = DEFAULT_HOLD_DURATION, unit: TimeUnit = DEFAULT_HOLD_UNIT, creator: (PARAM) -> T): ReadOnlyProperty<R, (PARAM) -> T> {
  return weakFun(WeakCacheHolder(duration, unit), creator)
}

/**
 * Returns weak cache instance delegate function property.
 *
 * @param duration instance hold duration.
 * @param unit instance hold unit.
 * @param creator instance creator.
 * @since 1.0
 */
fun<R, T, P1, P2> weakCacheFun(duration: Long = DEFAULT_HOLD_DURATION, unit: TimeUnit = DEFAULT_HOLD_UNIT, creator: (P1, P2) -> T): ReadOnlyProperty<R, (P1, P2) -> T> {
  return weakFun(WeakCacheHolder(duration, unit), creator)
}

/**
 * Cache holder.
 *
 * @author verstsiu on 2018/6/15.
 * @version 1.0
 */
private class CacheHolder<T>(duration: Long, unit: TimeUnit): WeakHolder<T> {

  private val cache = CacheBuilder
      .newBuilder()
      .expireAfterAccess(duration, unit)
      .build<String, T>()

  override fun get() = cache.getIfPresent(QUERY_KEY)

  override fun set(instance: T?) {
    if (instance == null) {
      cache.cleanUp()
    } else {
      cache.put(QUERY_KEY, instance)
    }
  }

  companion object {
    private const val QUERY_KEY = "weak_holder"
  }
}

/**
 * Weak cache holder.
 *
 * @author verstsiu on 2018/6/15.
 * @version 1.0
 */
private class WeakCacheHolder<T>(duration: Long, unit: TimeUnit): WeakHolder<T> {

  private var refInstance: WeakReference<T>? = null

  private val cache = CacheBuilder
      .newBuilder()
      .expireAfterAccess(duration, unit)
      .build<String, T>()

  override fun get(): T? {
    val cachedValue = cache.getIfPresent(QUERY_KEY)

    if (cachedValue != null) {
      return cachedValue
    }
    val refValue = refInstance?.get()

    if (refValue != null) {
      cache.put(QUERY_KEY, refValue)
      return cache.getIfPresent(QUERY_KEY)
    }
    return null
  }

  override fun set(instance: T?) {
    refInstance = if (instance == null) {
      cache.cleanUp()
      null
    }
    else {
      cache.put(QUERY_KEY, instance)
      WeakReference(instance)
    }
  }

  companion object {
    private const val QUERY_KEY = "weak_holder"
  }
}