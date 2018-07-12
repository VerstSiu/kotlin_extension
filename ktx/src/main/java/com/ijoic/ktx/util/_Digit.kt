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
@file:kotlin.jvm.JvmMultifileClass
@file:kotlin.jvm.JvmName("DigitKt")

package com.ijoic.ktx.util

private val charItemsBase = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
private val charItemsLowerCase = charItemsBase.plus(charArrayOf('a', 'b', 'c', 'd', 'e', 'f'))
private val charItemsUpperCase = charItemsBase.plus(charArrayOf('A', 'B', 'C', 'D', 'E', 'F'))

/**
 * Parse int value to hex string.
 *
 * @param upperCase upper case status.
 */
@JvmOverloads
fun Int.toHex(upperCase: Boolean = false): String {
  val builder = StringBuilder()

  toByteHex(builder, this shr 24, upperCase)
  toByteHex(builder, this shr 16, upperCase)
  toByteHex(builder, this shr 8, upperCase)
  toByteHex(builder, this, upperCase)

  return builder.toString()
}

/**
 * Parse int value to byte hex string.
 *
 * @param builder string builder.
 * @param digit measure digit.
 * @param upperCase upper case status.
 */
private fun toByteHex(builder: StringBuilder, digit: Int, upperCase: Boolean = false) {
  val byte = digit and 0xFF

  builder.append(toBinaryCharacter(byte shr 4, upperCase))
  builder.append(toBinaryCharacter(byte, upperCase))
}

/**
 * Parse int value to byte hex string.
 *
 * @param digit measure digit.
 * @param upperCase upper case status.
 */
private fun toBinaryCharacter(digit: Int, upperCase: Boolean = false): Char {
  return (upperCase.matchTexture(charItemsUpperCase, charItemsLowerCase))[digit and 0x0F]
}