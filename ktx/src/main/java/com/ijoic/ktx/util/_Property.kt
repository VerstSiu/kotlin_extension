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

import kotlin.reflect.KMutableProperty0

/**
 * Returns cached or new created item instance.
 *
 * @param creator item creator.
 * @since 1.0
 */
fun<T> KMutableProperty0<T?>.getOrCreate(creator: () -> T): T {
  return get().orCreate(creator) { set(it) }
}

/**
 * Returns replaced new created item instance.
 *
 * @param creator item creator.
 * @since 1.0
 */
fun<T> KMutableProperty0<T?>.replace(creator: () -> T): T {
  val replaceValue = creator.invoke()
  set(replaceValue)
  return replaceValue
}