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
package com.ijoic.ktx.util

/**
 * Returns matched texture value.
 *
 * @param valueTrue value true.
 * @param valueFalse value false.
 */
fun<T> Boolean.matchTexture(valueTrue: T, valueFalse: T): T {
  return if (this) {
    valueTrue
  } else {
    valueFalse
  }
}

/**
 * Returns matched texture value.
 *
 * @param valueTrue value true.
 * @param valueFalse value false.
 * @param valueDefault value default.
 */
fun<T> Boolean?.matchTexture(valueTrue: T, valueFalse: T, valueDefault: T): T {
  return when(this) {
    null -> valueDefault
    true -> valueTrue
    false -> valueFalse
  }
}

/**
 * Run action when current value is exactly true.
 *
 * @param action action.
 */
fun Boolean?.matchTrue(action: () -> Unit) {
  if (this == true) {
    action.invoke()
  }
}

/**
 * Run action when current value is exactly false.
 *
 * @param action action.
 */
fun Boolean?.matchFalse(action: () -> Unit) {
  if (this == false) {
    action.invoke()
  }
}