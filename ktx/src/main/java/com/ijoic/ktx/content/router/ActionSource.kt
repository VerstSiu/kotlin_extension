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
package com.ijoic.ktx.content.router

import com.ijoic.ktx.util.AppState
import com.ijoic.ktx.util.matchTexture
import com.ijoic.ktx.util.orCreate

/**
 * Action source.
 *
 * @author verstsiu on 2018/7/18.
 * @version 1.0
 */
abstract class ActionSource(module: String? = null) {
  private val modulePrefix = module.isNullOrEmpty().matchTexture("", "$module.")

  /**
   * Returns root action.
   *
   * @param suffix action suffix.
   */
  protected fun bindRootAction(suffix: String) = "$prefix.$suffix"

  /**
   * Returns child action.
   *
   * @param suffix action suffix.
   */
  protected fun bindChildAction(suffix: String) = "$prefix.$modulePrefix$suffix"

  private var cachedPrefix: String? = null
  private val prefix: String
    get() = cachedPrefix.orCreate { initPackagePrefix() }

  private fun initPackagePrefix(): String {
    val context = AppState.getContext()
    val prefix = context?.packageName

    if (prefix != null) {
      cachedPrefix = prefix
    }
    return prefix ?: Router.defPackage
  }

}