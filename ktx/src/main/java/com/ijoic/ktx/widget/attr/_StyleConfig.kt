package com.ijoic.ktx.widget.attr

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.AttrRes
import com.ijoic.ktx.util.valuebox.ValueBox
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Bind text.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindText(@AttrRes attr: Int, defValue: String? = null) = bindAttrItem(attr, defValue) { array, index ->
  array.getText(index)
}

/**
 * Bind string.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindString(@AttrRes attr: Int, defValue: String? = null) = bindAttrItem(attr, defValue) { array, index ->
  array.getString(index)
}

/**
 * Bind non-resource string.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindNonResourceString(@AttrRes attr: Int, defValue: String? = null) = bindAttrItem(attr, defValue) { array, index ->
  array.getNonResourceString(index)
}

/**
 * Bind boolean.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindBoolean(@AttrRes attr: Int, defValue: Boolean = false) = bindAttrItem(attr, defValue) { array, index ->
  array.getBoolean(index, defValue)
}

/**
 * Bind int.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindInt(@AttrRes attr: Int, defValue: Int = 0) = bindAttrItem(attr, defValue) { array, index ->
  array.getInt(index, defValue)
}

/**
 * Bind float.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindFloat(@AttrRes attr: Int, defValue: Float = 0F) = bindAttrItem(attr, defValue) { array, index ->
  array.getFloat(index, defValue)
}

/**
 * Bind color.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindColor(@AttrRes attr: Int, defValue: Int = Color.TRANSPARENT) = bindAttrItem(attr, defValue) { array, index ->
  array.getColor(index, defValue)
}

/**
 * Bind color state list.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindColorStateList(@AttrRes attr: Int, defValue: ColorStateList? = null) = bindAttrItem(attr, defValue) { array, index ->
  array.getColorStateList(index)
}

/**
 * Bind integer.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindInteger(@AttrRes attr: Int, defValue: Int = 0) = bindAttrItem(attr, defValue) { array, index ->
  array.getInteger(index, defValue)
}

/**
 * Bind dimension.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindDimen(@AttrRes attr: Int, defValue: Float = 0F) = bindAttrItem(attr, defValue) { array, index ->
  array.getDimension(index, defValue)
}

/**
 * Bind dimension pixel offset.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindDimenPixelOffset(@AttrRes attr: Int, defValue: Int = 0) = bindAttrItem(attr, defValue) { array, index ->
  array.getDimensionPixelOffset(index, defValue)
}

/**
 * Bind dimension pixel size.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindDimenPixelSize(@AttrRes attr: Int, defValue: Int = 0) = bindAttrItem(attr, defValue) { array, index ->
  array.getDimensionPixelSize(index, defValue)
}

/**
 * Bind fraction.
 *
 * @param attr attribute id.
 * @param base base value of this fraction.
 * @param pbase The parent base value of this fraction.
 * @param defValue default value.
 *
 * @see TypedArray.getFraction
 */
fun StyleConfig.bindFraction(@AttrRes attr: Int, base: Int, pbase: Int, defValue: Float = 0F) = bindAttrItem(attr, defValue) { array, index ->
  array.getFraction(index, base, pbase, defValue)
}

/**
 * Bind resource id.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindResourceId(@AttrRes attr: Int, defValue: Int = 0) = bindAttrItem(attr, defValue) { array, index ->
  array.getResourceId(index, defValue)
}

/**
 * Bind drawable.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindDrawable(@AttrRes attr: Int, defValue: Drawable? = null) = bindAttrItem(attr, defValue) { array, index ->
  array.getDrawable(index)
}

/**
 * Bind text array.
 *
 * @param attr attribute id.
 * @param defValue default value.
 */
fun StyleConfig.bindTextArray(@AttrRes attr: Int, defValue: Array<CharSequence>? = null) = bindAttrItem(attr, defValue) { array, index ->
  array.getTextArray(index)
}

/**
 * Bind attribute item.
 */
private fun<T> StyleConfig.bindAttrItem(@AttrRes attr: Int, defValue: T, getValue: (TypedArray, Int) -> T): ReadWriteProperty<StyleConfig, T> {
  val valueBox = ValueBox(defValue)
  val item = AttrItemImpl(attr, valueBox, getValue)

  attrItems.add(item)
  return SimpleProperty(valueBox)
}

/**
 * Simple property.
 */
private class SimpleProperty<R, T>(private var innerBox: ValueBox<T>): ReadWriteProperty<R, T> {

  override fun getValue(thisRef: R, property: KProperty<*>): T {
    return innerBox.value
  }

  override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
    innerBox.value = value
  }

}