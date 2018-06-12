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
 * Returns current or new created instance.
 *
 * @param creator creator.
 * @since 1.0
 */
fun<T> T?.orCreate(creator: () -> T): T {
  if (this != null) {
    return this
  }
  return creator.invoke()
}

/**
 * Returns current or new created instance.
 *
 * @param creator creator.
 * @param onCreate create action.
 * @since 1.0
 */
fun<T> T?.orCreate(creator: () -> T, onCreate: (T) -> Unit): T {
  if (this != null) {
    return this
  }
  val result = creator.invoke()
  onCreate.invoke(result)
  return result
}
