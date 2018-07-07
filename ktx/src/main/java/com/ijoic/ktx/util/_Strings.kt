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

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Convert string to json object.
 */
fun String?.toJSONObject(): JSONObject? {
  if (this != null && this.isNotEmpty()) {
    try {
      return JSONObject(this)

    } catch (e: JSONException) {
      e.printStackTrace()
    }
  }
  return null
}

/**
 * Convert string to json array.
 */
fun String?.toJSONArray(): JSONArray? {
  if (this != null && this.isNotEmpty()) {
    try {
      return JSONArray(this)

    } catch (e: JSONException) {
      e.printStackTrace()
    }
  }
  return null
}