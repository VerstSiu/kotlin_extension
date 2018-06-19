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
package usecase.ijoic.ktx.util

import com.ijoic.ktx.util.weak.weak
import com.ijoic.ktx.util.weak.weakFun

/**
 * Weak instance case.
 *
 * @author verstsiu on 2018/6/19.
 * @version 1.0
 */
class WeakInstanceCase {
  private val propWeak by weak { "" }

  private val propWeakFun by weakFun { _: String -> "" }

  fun test() {
    println(propWeak)
  }

  companion object {
    private val compWeakFun by weakFun { _: String -> WeakInstanceCase() }
  }
}