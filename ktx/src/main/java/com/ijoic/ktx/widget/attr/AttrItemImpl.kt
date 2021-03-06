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
package com.ijoic.ktx.widget.attr

import android.content.res.TypedArray
import androidx.annotation.AttrRes
import com.ijoic.ktx.util.valuebox.ValueBox

/**
 * Attribute item implement.
 *
 * @author verstsiu on 2018/7/7.
 * @version 1.0
 */
internal class AttrItemImpl<T>(
    @AttrRes private val attribute: Int,
    private val valueBox: ValueBox<T>,
    private val getPropertyValue: (TypedArray, Int) -> T): AttrItem {

  override fun getAttribute(): Int {
    return attribute
  }

  override fun applyInit(array: TypedArray, index: Int) {
    valueBox.value = getPropertyValue(array, index)
  }

}