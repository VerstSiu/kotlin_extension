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

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

/**
 * Weak cache map.
 *
 * @author verstsiu on 2018/6/25.
 * @version 1.0
 */
class WeakCacheMap<K, V> : MutableMap<K, V> {

  private val refMap = mutableMapOf<K, WeakReference<V>>()
  private val cacheMap: Cache<K, V>

  constructor(getCache: () -> Cache<K, V>) {
    cacheMap = getCache()
  }

  constructor(duration: Long = 2000L, unit: TimeUnit = TimeUnit.MILLISECONDS) {
    cacheMap = CacheBuilder
        .newBuilder()
        .expireAfterAccess(duration, unit)
        .build<K, V>()
  }

  override val size: Int
    get() {
      trimRefItems()
      return refMap.size
    }

  override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
    get() {
      trimRefItems()
      val result = mutableSetOf<MutableMap.MutableEntry<K, V>>()

      refMap.forEach {
        val itemKey = it.key
        val itemValue = it.value.get()

        if (itemKey != null && itemValue != null) {
          result.add(WeakCacheEntry(refMap, itemKey, itemValue))
        }
      }
      return result
    }

  override val keys: MutableSet<K>
    get() {
      trimRefItems()
      return refMap.keys
    }

  override val values: MutableCollection<V>
    get() {
      trimRefItems()
      val values = mutableSetOf<V>()

      refMap.forEach {
        val itemValue = it.value.get()

        if (itemValue != null) {
          values.add(itemValue)
        }
      }
      return values
    }

  override fun containsKey(key: K): Boolean {
    return get(key) != null
  }

  override fun get(key: K): V? {
    if (key == null) {
      return null
    }
    return refMap[key]?.get()
  }

  override fun isEmpty(): Boolean {
    trimRefItems()
    return refMap.isEmpty()
  }

  override fun clear() {
    refMap.clear()
    cacheMap.cleanUp()
  }

  override fun put(key: K, value: V): V? {
    if (key != null && value != null) {
      cacheMap.put(key, value)
      refMap[key] = WeakReference(value)
      return value
    }
    return null
  }

  override fun putAll(from: Map<out K, V>) {
    from.forEach { put(it.key, it.value) }
  }

  override fun remove(key: K): V? {
    if (key == null) {
      return null
    }
    return refMap.remove(key)?.get()
  }

  override fun containsValue(value: V): Boolean {
    return refMap.any { it.value.get() == value }
  }

  private fun trimRefItems() {
    val removedItemKeys = mutableListOf<K>()

    refMap.forEach {
      val itemKey = it.key

      if (itemKey != null && it.value.get() == null) {
        removedItemKeys.add(itemKey)
      }
    }
    removedItemKeys.forEach { refMap.remove(it) }
  }

  private class WeakCacheEntry<K, V>(
      private val refMap: MutableMap<K, WeakReference<V>>,
      override val key: K,
      private var currValue: V) : MutableMap.MutableEntry<K, V> {

    override val value: V
      get() = currValue

    override fun setValue(newValue: V): V {
      if (key != null) {
        if (newValue == null) {
          refMap.remove(key)
        } else {
          refMap[key] = WeakReference(newValue)
        }
      }
      currValue = newValue
      return newValue
    }
  }
}