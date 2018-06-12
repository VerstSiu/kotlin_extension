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
package com.ijoic.ktx.test

import junit.framework.Assert.*
import kotlin.reflect.KProperty0

/**
 * Assert item exist status.
 *
 * @param checkAction check action.
 * @since 1.0
 */
fun<T> T?.assertExist(checkAction: (T.() -> Unit)? = null) {
  assertNotNull(this)
  checkAction?.invoke(this!!)
}

/**
 * Assert item not exist status.
 *
 * @since 1.0
 */
fun<T> T?.assertNotExist() {
  assertNull(this)
}

/**
 * Assert property value matched.
 *
 * @param property property.
 * @param value value.
 * @since 1.0
 */
fun<T> assertProperty(property: KProperty0<T>, value: T) {
  assertEquals(property.get(), value)
}