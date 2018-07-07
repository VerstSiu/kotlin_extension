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
@file:kotlin.jvm.JvmMultifileClass
@file:kotlin.jvm.JvmName("SharedPrefsKt")

package com.ijoic.ktx.util.preference

import android.content.SharedPreferences

/**
 * Apply int value.
 *
 * @param key key.
 * @param value value.
 */
fun SharedPreferences.applyInt(key: String, value: Int) {
  edit().putInt(key, value).apply()
}

/**
 * Apply long value.
 *
 * @param key key.
 * @param value value.
 */
fun SharedPreferences.applyLong(key: String, value: Long) {
  edit().putLong(key, value).apply()
}

/**
 * Apply float value.
 *
 * @param key key.
 * @param value value.
 */
fun SharedPreferences.applyFloat(key: String, value: Float) {
  edit().putFloat(key, value).apply()
}

/**
 * Apply boolean value.
 *
 * @param key key.
 * @param value value.
 */
fun SharedPreferences.applyBoolean(key: String, value: Boolean) {
  edit().putBoolean(key, value).apply()
}

/**
 * Apply string value.
 *
 * @param key key.
 * @param value value.
 */
fun SharedPreferences.applyString(key: String, value: String) {
  edit().putString(key, value).apply()
}

/**
 * Apply string set value.
 *
 * @param key key.
 * @param value value.
 */
fun SharedPreferences.applyStringSet(key: String, value: Set<String>?) {
  edit().putStringSet(key, value).apply()
}

/**
 * Apply remove.
 *
 * @param key key.
 */
fun SharedPreferences.applyRemove(key: String) {
  edit().remove(key).apply()
}

/**
 * Apply clear.
 */
fun SharedPreferences.applyClear() {
  edit().clear().apply()
}