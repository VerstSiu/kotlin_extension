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

/**
 * Preferences child.
 *
 * @author verstsiu on 2018/9/28
 * @version 1.1
 */
open class PrefsChild: PrefsSource {

  /**
   * Child name.
   *
   * <p>Do init this property with non-empty string right after instance created.</p>
   */
  internal var name: String? = null

  /**
   * Child source.
   *
   * <p>Do init this property with non-empty prefs source right after instance created.</p>
   */
  internal var source: PrefsSource? = null

  override val innerPrefs: SharedPreferences
    get() = source!!.innerPrefs

  override fun genMapKey(key: String): String {
    return onGenMapKey(name!!, key)
  }

  /**
   * Generate map key callback.
   *
   * <p>Override this method if you want to have custom prefs key map rules.</p>
   *
   * @param name child name.
   * @param key prefs key.
   */
  protected open fun onGenMapKey(name: String, key: String): String {
    return "$name::$key"
  }

}