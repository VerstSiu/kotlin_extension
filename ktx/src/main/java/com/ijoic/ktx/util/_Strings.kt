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