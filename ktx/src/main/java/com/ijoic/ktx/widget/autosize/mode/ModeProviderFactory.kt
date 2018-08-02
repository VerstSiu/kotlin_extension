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
package com.ijoic.ktx.widget.autosize.mode

/**
 * Mode provider factory.
 *
 * @author verstsiu on 2018/7/31.
 * @version 1.0
 */
internal object ModeProviderFactory {

  private const val MODE_NONE = 0
  private const val MODE_WRAP = 1
  private const val MODE_RESET = 2

  internal const val DEFAULT_AUTO_SIZE_MODE = MODE_WRAP

  /**
   * Returns provider instance.
   *
   * @param mode auto size mode.
   */
  internal fun getProvider(mode: Int): ModeProvider = when(mode) {
    MODE_NONE -> EmptyModeProvider
    MODE_WRAP -> WrapMaxProvider()
    MODE_RESET -> ResetMaxProvider()
    else -> EmptyModeProvider
  }
}