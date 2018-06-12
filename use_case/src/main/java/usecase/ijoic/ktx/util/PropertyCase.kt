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

/**
 * Property use case.
 *
 * @author verstsiu on 2018/6/12.
 * @version 1.0
 */
internal open class PropertyCase {

  var propPublic: String? = null
  internal var propInternal: String? = null
  protected var propProtected: String? = null
  private var propPrivate: String? = null

  /**
   * Check property field type.
   */
  private fun checkPropFieldType() {
    val propPublic = this::propPublic
    val propInternal = this::propInternal
    val propProtected = this::propProtected
    val propPrivate = this::propPrivate
  }

}