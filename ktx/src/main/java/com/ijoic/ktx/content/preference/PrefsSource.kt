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
 * Preferences source.
 *
 * @author verstsiu on 2018/9/28
 * @version 1.1
 */
interface PrefsSource {
  /**
   * Inner preferences.
   */
  val innerPrefs: SharedPreferences

  /**
   * Generate mapped preferences key.
   */
  fun genMapKey(key: String): String = key
}