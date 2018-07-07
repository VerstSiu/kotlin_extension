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
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Bind int property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun AccessPreference.bindInt(key: String, defValue: Int? = null, getDefault: (() -> Int)? = null): ReadWriteProperty<AccessPreference, Int> = AccessProperty(
    { getInt(key, defValue ?: getDefault?.invoke() ?: 0) },
    { applyInt(key, it) }
)

/**
 * Bind long property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun AccessPreference.bindLong(key: String, defValue: Long? = null, getDefault: (() -> Long)? = null): ReadWriteProperty<AccessPreference, Long> = AccessProperty(
    { getLong(key, defValue ?: getDefault?.invoke() ?: 0) },
    { applyLong(key, it) }
)

/**
 * Bind float property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun AccessPreference.bindFloat(key: String, defValue: Float? = null, getDefault: (() -> Float)? = null): ReadWriteProperty<AccessPreference, Float> = AccessProperty(
    { getFloat(key, defValue ?: getDefault?.invoke() ?: 0F) },
    { applyFloat(key, it) }
)

/**
 * Bind boolean property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun AccessPreference.bindBoolean(key: String, defValue: Boolean? = null, getDefault: (() -> Boolean)? = null): ReadWriteProperty<AccessPreference, Boolean> = AccessProperty(
    { getBoolean(key, defValue ?: getDefault?.invoke() ?: false) },
    { applyBoolean(key, it) }
)

/**
 * Bind string property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun AccessPreference.bindString(key: String, defValue: String? = null, getDefault: (() -> String)? = null): ReadWriteProperty<AccessPreference, String> = AccessProperty(
    { getString(key, defValue ?: getDefault?.invoke() ?: "") },
    { applyString(key, it) }
)

/**
 * Bind string set property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun AccessPreference.bindStringSet(key: String, defValue: Set<String>? = null, getDefault: (() -> Set<String>)? = null): ReadWriteProperty<AccessPreference, Set<String>> = AccessProperty(
    { getStringSet(key, defValue ?: getDefault?.invoke() ?: emptySet()) },
    { applyStringSet(key, it) }
)

/**
 * Bind optional string property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun AccessPreference.bindOptionalString(key: String, defValue: String? = null, getDefault: (() -> String?)? = null): ReadWriteProperty<AccessPreference, String?> = AccessProperty(
    { getString(key, getDefault?.invoke() ?: defValue) },
    { applyString(key, it) }
)

/**
 * Bind optional string set property.
 *
 * @param key prefs item key.
 * @param defValue prefs item default value.
 * @param getDefault prefs item default value getter.
 */
fun AccessPreference.bindOptionalStringSet(key: String, defValue: Set<String>? = null, getDefault: (() -> Set<String>?)? = null): ReadWriteProperty<AccessPreference, Set<String>?> = AccessProperty(
    { getStringSet(key, getDefault?.invoke() ?: defValue) },
    { applyStringSet(key, it) }
)

/**
 * Access property.
 */
private class AccessProperty<T>(
    private val getter: SharedPreferences.() -> T,
    private val setter: SharedPreferences.(T) -> Unit): ReadWriteProperty<AccessPreference, T> {

  override fun getValue(thisRef: AccessPreference, property: KProperty<*>): T {
    return getter.invoke(thisRef.prefs)
  }

  override fun setValue(thisRef: AccessPreference, property: KProperty<*>, value: T) {
    setter.invoke(thisRef.prefs, value)
  }

}