package com.ijoic.ktx.util

import java.lang.Exception

/**
 * Execute [func] ignore error
 */
inline fun ignoreError(func: () -> Unit) {
  try {
    func.invoke()
  } catch (e: Exception) {
    // do nothing
  }
}

/**
 * Execute [func] ignore error, and print stacktrace when error occurs
 */
inline fun ignoreErrorTraced(func: () -> Unit) {
  try {
    func.invoke()
  } catch (e: Exception) {
    e.printStackTrace()
  }
}