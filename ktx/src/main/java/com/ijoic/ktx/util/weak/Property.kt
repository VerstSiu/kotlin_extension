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
package com.ijoic.ktx.util.weak

import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Returns weak reference delegate property.
 *
 * @since 1.0
 */
fun<R, T> weak() = object: ReadWriteProperty<R, T?> {

  private var refItem: WeakReference<T>? = null

  override fun getValue(thisRef: R, property: KProperty<*>): T? {
    return refItem?.get()
  }

  override fun setValue(thisRef: R, property: KProperty<*>, value: T?) {
    if (value == null) {
      refItem = null
    } else {
      refItem = WeakReference(value)
    }
  }

}