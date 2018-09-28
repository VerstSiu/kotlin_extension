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
package com.ijoic.ktx.content.preference

import android.content.SharedPreferences
import com.ijoic.ktx.util.getOrCreate
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Bind int property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun PrefsSource.bindInt(key: String, defValue: Int? = null, getDefault: (() -> Int)? = null): ReadWriteProperty<PrefsSource, Int> = AccessProperty(
    { genMapKey(key) },
    { getInt(it, defValue ?: getDefault?.invoke() ?: 0) },
    SharedPreferences::applyInt
)

/**
 * Bind long property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun PrefsSource.bindLong(key: String, defValue: Long? = null, getDefault: (() -> Long)? = null): ReadWriteProperty<PrefsSource, Long> = AccessProperty(
    { genMapKey(key) },
    { getLong(it, defValue ?: getDefault?.invoke() ?: 0) },
    SharedPreferences::applyLong
)

/**
 * Bind float property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun PrefsSource.bindFloat(key: String, defValue: Float? = null, getDefault: (() -> Float)? = null): ReadWriteProperty<PrefsSource, Float> = AccessProperty(
    { genMapKey(key) },
    { getFloat(it, defValue ?: getDefault?.invoke() ?: 0F) },
    SharedPreferences::applyFloat
)

/**
 * Bind boolean property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun PrefsSource.bindBoolean(key: String, defValue: Boolean? = null, getDefault: (() -> Boolean)? = null): ReadWriteProperty<PrefsSource, Boolean> = AccessProperty(
    { genMapKey(key) },
    { getBoolean(it, defValue ?: getDefault?.invoke() ?: false) },
    SharedPreferences::applyBoolean
)

/**
 * Bind string property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun PrefsSource.bindString(key: String, defValue: String? = null, getDefault: (() -> String)? = null): ReadWriteProperty<PrefsSource, String> = AccessProperty(
    { genMapKey(key) },
    { getString(it, defValue ?: getDefault?.invoke() ?: "") },
    SharedPreferences::applyString
)

/**
 * Bind string set property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun PrefsSource.bindStringSet(key: String, defValue: Set<String>? = null, getDefault: (() -> Set<String>)? = null): ReadWriteProperty<PrefsSource, Set<String>> = AccessProperty(
    { genMapKey(key) },
    { getStringSet(it, defValue ?: getDefault?.invoke() ?: emptySet()) },
    SharedPreferences::applyStringSet
)

/**
 * Bind optional string property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun PrefsSource.bindOptionalString(key: String, defValue: String? = null, getDefault: (() -> String?)? = null): ReadWriteProperty<PrefsSource, String?> = AccessProperty(
    { genMapKey(key) },
    { getString(it, getDefault?.invoke() ?: defValue) },
    SharedPreferences::applyString
)

/**
 * Bind optional string set property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun PrefsSource.bindOptionalStringSet(key: String, defValue: Set<String>? = null, getDefault: (() -> Set<String>?)? = null): ReadWriteProperty<PrefsSource, Set<String>?> = AccessProperty(
    { genMapKey(key) },
    { getStringSet(it, getDefault?.invoke() ?: defValue) },
    SharedPreferences::applyStringSet
)

/**
 * Bind group.
 *
 * @param name group name.
 * @param createChild create group child instance.
 *
 * @see PrefsGroup
 * @see PrefsChild
 */
fun<T: PrefsChild> PrefsSource.bindGroup(name: String, createChild: () -> T): ReadOnlyProperty<PrefsSource, PrefsGroup<T>> {
  return bindGroup(name, { group, child -> "$group::$child" }, createChild)
}

/**
 * Bind group.
 *
 * @param name group name.
 * @param createChild create group child instance.
 * @param mapChildName map child name.
 *
 * @see PrefsGroup
 * @see PrefsChild
 */
fun<T: PrefsChild> PrefsSource.bindGroup(name: String, mapChildName: (String, String) -> String, createChild: () -> T) = object: ReadOnlyProperty<PrefsSource, PrefsGroup<T>> {

  private var instance: PrefsGroup<T>? = null

  override fun getValue(thisRef: PrefsSource, property: KProperty<*>): PrefsGroup<T> {
    return this::instance.getOrCreate {
      PrefsGroup(name, thisRef, createChild, mapChildName)
    }
  }
}

/**
 * Access property.
 */
private class AccessProperty<T>(
    getKey: () -> String,
    private val getter: SharedPreferences.(String) -> T,
    private val setter: SharedPreferences.(String, T) -> Unit): ReadWriteProperty<PrefsSource, T> {

  private val key by lazy(getKey)

  override fun getValue(thisRef: PrefsSource, property: KProperty<*>): T {
    return getter.invoke(thisRef.innerPrefs, key)
  }

  override fun setValue(thisRef: PrefsSource, property: KProperty<*>, value: T) {
    setter.invoke(thisRef.innerPrefs, key, value)
  }

}