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
package com.ijoic.ktx.widget.autosize

import android.content.Context
import android.content.res.TypedArray
import android.graphics.RectF
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import com.ijoic.ktx.R
import com.ijoic.ktx.test.debug.DebugSource
import com.ijoic.ktx.test.debug.initDebugStatus
import com.ijoic.ktx.test.debug.printStateMessage
import com.ijoic.ktx.util.getOrCreate
import com.ijoic.ktx.util.replaceExist
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Utility class which encapsulates the logic for the TextView auto-size text feature added to
 * the Android Framework in [android.os.Build.VERSION_CODES.O].
 *
 *
 * A TextView can be instructed to let the size of the text expand or contract automatically to
 * fill its layout based on the TextView's characteristics and boundaries.
 */
internal class AutoSizeTextHelper internal constructor(private val mTextView:TextView): DebugSource {
  private val context: Context = mTextView.context
  override var debugEnabled = false

  // Specify if auto-size text is needed.
  private var mNeedsAutoSizeText = false

  // Step size for auto-sizing in pixels.
  private var mAutoSizeStepGranularityInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
  // Minimum text size for auto-sizing in pixels.
  private var mAutoSizeMinTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
  // Maximum text size for auto-sizing in pixels.
  private var mAutoSizeMaxTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE

  // Contains a (specified or computed) distinct sorted set of text sizes in pixels to pick from
  // when auto-sizing text.
  /**
  * @return the current auto-size `int` sizes array (in pixels).
  *
  * @see .setAutoSizeTextTypeUniformWithConfiguration
  * @see .setAutoSizeTextTypeUniformWithPresetSizes
  * @hide
  */
  private var autoSizeTextAvailableSizes = IntArray(0)

  // Specifies whether auto-size should use the provided auto size steps set or if it should
  // build the steps set using mAutoSizeMinTextSizeInPx, mAutoSizeMaxTextSizeInPx and
  // mAutoSizeStepGranularityInPx.
  private var mHasPresetAutoSizeValues = false
  private var mTempTextPaint:TextPaint? = null

  /**
   * Measure info.
   */
  internal val measureInfo by lazy { AutoSizeTextInfo() }

  private var maxHistoryMeasuredWidth: Int? = null
  private var maxHistoryLayoutHeight: Int? = null

  internal fun loadFromAttributes(attrs:AttributeSet?, defStyleAttr:Int) {
    initDebugStatus(context, attrs, defStyleAttr)
    var autoSizeMinTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
    var autoSizeMaxTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
    var autoSizeStepGranularityInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE

    val a = context.obtainStyledAttributes(attrs, R.styleable.AutoSizeTextView, defStyleAttr, 0)
    if (a.hasValue(R.styleable.AutoSizeTextView_ktx_autoSizeStepGranularity)) {
      autoSizeStepGranularityInPx = a.getDimension(
          R.styleable.AutoSizeTextView_ktx_autoSizeStepGranularity,
          UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE)
    }
    if (a.hasValue(R.styleable.AutoSizeTextView_ktx_autoSizeMinTextSize)) {
      autoSizeMinTextSizeInPx = a.getDimension(
          R.styleable.AutoSizeTextView_ktx_autoSizeMinTextSize,
          UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE)
    }
    if (a.hasValue(R.styleable.AutoSizeTextView_ktx_autoSizeMaxTextSize)) {
      autoSizeMaxTextSizeInPx = a.getDimension(
          R.styleable.AutoSizeTextView_ktx_autoSizeMaxTextSize,
          UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE)
    }
    if (a.hasValue(R.styleable.AutoSizeTextView_ktx_autoSizePresetSizes)) {
      val autoSizeStepSizeArrayResId = a.getResourceId(R.styleable.AutoSizeTextView_ktx_autoSizePresetSizes, 0)

      if (autoSizeStepSizeArrayResId > 0) {
        val autoSizePreDefTextSizes = a.resources.obtainTypedArray(autoSizeStepSizeArrayResId)
        setupAutoSizeUniformPresetSizes(autoSizePreDefTextSizes)
        autoSizePreDefTextSizes.recycle()
      }
    }
    a.recycle()

    // If uniform auto-size has been specified but preset values have not been set then
    // replace the auto-size configuration values that have not been specified with the
    // defaults.
    if (!mHasPresetAutoSizeValues) {
      val displayMetrics = context.resources.displayMetrics

      if (autoSizeMinTextSizeInPx == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) {
        autoSizeMinTextSizeInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP.toFloat(),
            displayMetrics)
      }

      if (autoSizeMaxTextSizeInPx == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) {
        autoSizeMaxTextSizeInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP.toFloat(),
            displayMetrics)
      }

      if (autoSizeStepGranularityInPx == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) {
        autoSizeStepGranularityInPx = DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX.toFloat()
      }

      validateAndSetAutoSizeTextTypeUniformConfiguration(
          autoSizeMinTextSizeInPx,
          autoSizeMaxTextSizeInPx,
          autoSizeStepGranularityInPx)
    }

    setupAutoSizeText()
  }

  private fun setupAutoSizeUniformPresetSizes(textSizes:TypedArray) {
    val textSizesLength = textSizes.length()
    val parsedSizes = IntArray(textSizesLength)

    if (textSizesLength > 0) {
      for (i in 0 until textSizesLength) {
        parsedSizes[i] = textSizes.getDimensionPixelSize(i, -1)
      }
      autoSizeTextAvailableSizes = cleanupAutoSizePresetSizes(parsedSizes)
      setupAutoSizeUniformPresetSizesConfiguration()
    }
  }

  private fun setupAutoSizeUniformPresetSizesConfiguration():Boolean {
    val textSizes = autoSizeTextAvailableSizes
    val sizesLength = textSizes.size
    val hasPresetAutoSizeValues = this::mHasPresetAutoSizeValues.replaceExist { sizesLength > 0 }

    if (hasPresetAutoSizeValues) {
      mAutoSizeMinTextSizeInPx = textSizes[0].toFloat()
      mAutoSizeMaxTextSizeInPx = textSizes[sizesLength - 1].toFloat()
      mAutoSizeStepGranularityInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE
    }
    return hasPresetAutoSizeValues
  }

  // Returns distinct sorted positive values.
  private fun cleanupAutoSizePresetSizes(presetValues:IntArray):IntArray {
    val presetValuesLength = presetValues.size
    if (presetValuesLength == 0) {
      return presetValues
    }
    Arrays.sort(presetValues)

    val uniqueValidSizes = ArrayList<Int>()
    presetValues.forEach {
      if (it > 0 && Collections.binarySearch(uniqueValidSizes, it) < 0) {
        uniqueValidSizes.add(it)
      }
    }

    return if (presetValuesLength == uniqueValidSizes.size) {
      presetValues
    } else {
      uniqueValidSizes.toTypedArray().toIntArray()
    }
  }

  /**
   * If all params are valid then save the auto-size configuration.
   *
   * @throws IllegalArgumentException if any of the params are invalid
   */
  @Throws(IllegalArgumentException::class)
  private fun validateAndSetAutoSizeTextTypeUniformConfiguration(
      autoSizeMinTextSizeInPx:Float,
      autoSizeMaxTextSizeInPx:Float,
      autoSizeStepGranularityInPx:Float) {

    // First validate.
    if (autoSizeMinTextSizeInPx <= 0) {
      throw IllegalArgumentException("Minimum auto-size text size (" + autoSizeMinTextSizeInPx + "px) is less or equal to (0px)")
    }

    if (autoSizeMaxTextSizeInPx <= autoSizeMinTextSizeInPx) {
      throw IllegalArgumentException("Maximum auto-size text size (" + autoSizeMaxTextSizeInPx + "px) is less or equal to minimum auto-size " + "text size (" + autoSizeMinTextSizeInPx + "px)")
    }

    if (autoSizeStepGranularityInPx <= 0) {
      throw IllegalArgumentException("The auto-size step granularity (" + autoSizeStepGranularityInPx + "px) is less or equal to (0px)")
    }

    // All good, persist the configuration.
    mAutoSizeMinTextSizeInPx = autoSizeMinTextSizeInPx
    mAutoSizeMaxTextSizeInPx = autoSizeMaxTextSizeInPx
    mAutoSizeStepGranularityInPx = autoSizeStepGranularityInPx
    mHasPresetAutoSizeValues = false
  }

  private fun setupAutoSizeText():Boolean {
    // Calculate the sizes set based on minimum size, maximum size and step size if we do
    // not have a predefined set of sizes or if the current sizes array is empty.
    if (!mHasPresetAutoSizeValues || autoSizeTextAvailableSizes.isEmpty()) {
      // Calculate sizes to choose from based on the current auto-size configuration.
      var autoSizeValuesLength = 1
      var currentSize = Math.round(mAutoSizeMinTextSizeInPx).toFloat()
      while (Math.round(currentSize + mAutoSizeStepGranularityInPx) <= Math.round(mAutoSizeMaxTextSizeInPx)) {
        autoSizeValuesLength++
        currentSize += mAutoSizeStepGranularityInPx
      }
      val autoSizeTextSizesInPx = IntArray(autoSizeValuesLength)
      var sizeToAdd = mAutoSizeMinTextSizeInPx

      for (i in 0 until autoSizeValuesLength) {
        autoSizeTextSizesInPx[i] = Math.round(sizeToAdd)
        sizeToAdd += mAutoSizeStepGranularityInPx
      }
      autoSizeTextAvailableSizes = cleanupAutoSizePresetSizes(autoSizeTextSizesInPx)
    }

    return true
  }

  internal fun onLayout(height: Int) {
    val historyHeight = maxHistoryLayoutHeight

    if (height > 0 && (historyHeight == null || historyHeight < height)) {
      maxHistoryLayoutHeight = height
    }
    autoSizeText()
  }

  /**
   * Automatically computes and sets the text size.
   *
   * @hide
   */
  internal fun autoSizeText() {
    printStateMessage("size")

    if (mNeedsAutoSizeText) {
      if (mTextView.measuredHeight <= 0 || mTextView.measuredWidth <= 0) {
        printStateMessage("size") { "width or height empty [skip!!]" }
        return
      }

      val horizontallyScrolling = invokeAndReturnWithDefault(mTextView, "getHorizontallyScrolling", false)

      val historyWidth = measureInfo.getAvailableWidth()
      val availableWidth = when {
        historyWidth != null -> historyWidth - mTextView.totalPaddingLeft - mTextView.totalPaddingRight
        horizontallyScrolling -> VERY_WIDE
        else -> mTextView.measuredWidth - mTextView.totalPaddingLeft - mTextView.totalPaddingRight
      }

      val historyHeight = maxHistoryLayoutHeight
      val availableHeight = when {
        historyHeight != null -> historyHeight - mTextView.compoundPaddingBottom - mTextView.compoundPaddingTop
        else -> mTextView.height - mTextView.compoundPaddingBottom - mTextView.compoundPaddingTop
      }

      if (availableWidth <= 0 || availableHeight <= 0) {
        printStateMessage("size") { "available width or height empty [skip!!]" }
        return
      }
      printStateMessage("size") { "perform auto size: width - $availableWidth, height - $availableHeight" }

      synchronized (tempRectF) {
        tempRectF.apply {
          setEmpty()
          right = availableWidth.toFloat()
          bottom = availableHeight.toFloat()
        }

        val optimalTextSize = findLargestTextSizeWhichFits(tempRectF).toFloat()

        if (optimalTextSize != mTextView.textSize) {
          setTextSizeInternal(TypedValue.COMPLEX_UNIT_PX, optimalTextSize)
        }
      }
    } else {
      printStateMessage("size") { "un need auto size text [skip!!]" }
    }
    // Always try to auto-size if enabled. Functions that do not want to trigger auto-sizing
    // after the next layout pass should set this to false.
    mNeedsAutoSizeText = true
  }

  private fun setTextSizeInternal(unit:Int, size:Float) {
    val res = context.resources

    setRawTextSize(TypedValue.applyDimension(unit, size, res.displayMetrics))
  }

  private fun setRawTextSize(size:Float) {
    if (size != mTextView.paint.textSize) {
      mTextView.paint.textSize = size

      var isInLayout = false
      if (Build.VERSION.SDK_INT >= 18) {
        isInLayout = mTextView.isInLayout
      }

      if (mTextView.layout != null) {
        // Do not auto-size right after setting the text size.
        mNeedsAutoSizeText = false

        val methodName = "nullLayouts"
        try {
          val method = getTextViewMethod(methodName)
          method?.invoke(mTextView)

        } catch (ex:Exception) {
          Log.w(TAG, "Failed to invoke TextView#$methodName() method", ex)
        }

        if (!isInLayout) {
          mTextView.requestLayout()
        } else {
          mTextView.forceLayout()
        }

        mTextView.invalidate()
      }
    }
  }

  /**
   * Performs a binary search to find the largest text size that will still fit within the size
   * available to this view.
   */
  private fun findLargestTextSizeWhichFits(availableSpace:RectF):Int {
    val textSizes = autoSizeTextAvailableSizes
    val sizesCount = textSizes.size
    if (sizesCount == 0) {
      throw IllegalStateException("No available text sizes to choose from.")
    }

    var bestSizeIndex = 0
    var lowIndex = bestSizeIndex + 1
    var highIndex = sizesCount - 1
    var sizeToTryIndex:Int

    // check for max measured with changed
    val maxTextSize = textSizes[highIndex]

    if (upgradeMaxHistoryMeasuredWidth(maxTextSize)) {
      return maxTextSize
    }

    // find suggested text size
    while (lowIndex <= highIndex) {
      sizeToTryIndex = (lowIndex + highIndex) / 2

      if (suggestedSizeFitsInSpace(textSizes[sizeToTryIndex], availableSpace)) {
        bestSizeIndex = lowIndex
        lowIndex = sizeToTryIndex + 1
      } else {
        highIndex = sizeToTryIndex - 1
        bestSizeIndex = highIndex
      }
    }

    return textSizes[bestSizeIndex]
  }

  private fun upgradeMaxHistoryMeasuredWidth(textSizeInPx: Int): Boolean {
    val text = getDisplayText()
    val textPaint = getResetTextPaint(textSizeInPx)
    val measuredTextWidth = Math.round(textPaint.measureText(text.toString()))

    val historyWidth = maxHistoryMeasuredWidth
    val widthChanged = historyWidth == null || measuredTextWidth > historyWidth

    if (widthChanged) {
      maxHistoryMeasuredWidth = measuredTextWidth
    }
    return widthChanged
  }

  private fun suggestedSizeFitsInSpace(suggestedSizeInPx:Int, availableSpace:RectF):Boolean {
    val text = getDisplayText()
    val maxLines = if (Build.VERSION.SDK_INT >= 16) mTextView.maxLines else -1
    val textPaint = getResetTextPaint(suggestedSizeInPx)

    // Needs reflection call due to being private.
    val alignment = invokeAndReturnWithDefault(mTextView, "getLayoutAlignment", Layout.Alignment.ALIGN_NORMAL)
    val layout =  when {
      Build.VERSION.SDK_INT >= 23 -> createStaticLayoutForMeasuring(textPaint, text, alignment, Math.round(availableSpace.right), maxLines)
      else -> createStaticLayoutForMeasuringPre23(textPaint, text, alignment, Math.round(availableSpace.right))
    }

    // Lines overflow.
    if (maxLines != -1 && (layout.lineCount > maxLines || (layout.getLineEnd(layout.lineCount - 1)) != text.length)) {
      return false
    }

    // Height overflow.
    return layout.height <= availableSpace.bottom
  }

  /**
   * Returns display text.
   */
  private fun getDisplayText(): CharSequence {
    val text = mTextView.text
    val transformationMethod = mTextView.transformationMethod

    if (transformationMethod != null) {
      val transformedText = transformationMethod.getTransformation(text, mTextView)

      if (transformedText != null) {
        return transformedText
      }
    }
    return text
  }

  /**
   * Returns reset text paint.
   */
  private fun getResetTextPaint(textSizeInPx: Int): TextPaint {
    val textPaintExist = mTempTextPaint != null
    val textPaint = this::mTempTextPaint.getOrCreate { TextPaint() }

    if (textPaintExist) {
      textPaint.reset()
    }

    textPaint.set(mTextView.paint)
    textPaint.textSize = textSizeInPx.toFloat()

    return textPaint
  }

  @RequiresApi(23)
  private fun createStaticLayoutForMeasuring(textPaint: TextPaint, text:CharSequence, alignment: Layout.Alignment, availableWidth:Int, maxLines:Int):StaticLayout {
    // Can use the StaticLayout.Builder (along with TextView params added in or after
    // API 23) to construct the layout.
    val textDirectionHeuristic = invokeAndReturnWithDefault(mTextView, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR)
    val layoutBuilder = StaticLayout.Builder.obtain(text, 0, text.length, textPaint, availableWidth)

    return layoutBuilder.setAlignment(alignment)
        .setLineSpacing(mTextView.lineSpacingExtra, mTextView.lineSpacingMultiplier)
        .setIncludePad(mTextView.includeFontPadding)
        .setBreakStrategy(mTextView.breakStrategy)
        .setHyphenationFrequency(mTextView.hyphenationFrequency)
        .setMaxLines(if (maxLines == -1) Integer.MAX_VALUE else maxLines)
        .setTextDirection(textDirectionHeuristic)
        .build()
  }

  private fun createStaticLayoutForMeasuringPre23(textPaint: TextPaint, text:CharSequence, alignment:Layout.Alignment?, availableWidth:Int): StaticLayout {
    // Setup defaults.
    var lineSpacingMultiplier = 1.0f
    var lineSpacingAdd = 0.0f
    var includePad = true

    if (Build.VERSION.SDK_INT >= 16) {
      // Call public methods.
      lineSpacingMultiplier = mTextView.lineSpacingMultiplier
      lineSpacingAdd = mTextView.lineSpacingExtra
      includePad = mTextView.includeFontPadding
    } else {
      // Call private methods and make sure to provide fallback defaults in case something
      // goes wrong. The default values have been inlined with the StaticLayout defaults.
      lineSpacingMultiplier = invokeAndReturnWithDefault(mTextView, "getLineSpacingMultiplier", lineSpacingMultiplier)
      lineSpacingAdd = invokeAndReturnWithDefault(mTextView, "getLineSpacingExtra", lineSpacingAdd)
      includePad = invokeAndReturnWithDefault(mTextView, "getIncludeFontPadding", includePad)
    }

    // The layout could not be constructed using the builder so fall back to the
    // most broad constructor.
    return StaticLayout(text, textPaint, availableWidth,
        alignment,
        lineSpacingMultiplier,
        lineSpacingAdd,
        includePad)
  }

  private fun <T> invokeAndReturnWithDefault(obj:Any, methodName:String, defaultValue:T):T {
    var result:T? = null

    try {
      // Cache lookup.
      val method = getTextViewMethod(methodName)
      result = method?.invoke(obj) as? T
    } catch (ex:Exception) {
      Log.w(TAG, "Failed to invoke TextView#$methodName() method", ex)
    }

    return result ?: defaultValue
  }

  private fun getTextViewMethod(methodName:String):Method? {
    try {
      var method:Method? = textViewMethodByNameCache[methodName]
      if (method == null) {
        method = TextView::class.java.getDeclaredMethod(methodName)
        if (method != null) {
          method.isAccessible = true
          // Cache update.
          textViewMethodByNameCache[methodName] = method
        }
      }

      return method
    } catch (ex:Exception) {
      Log.w(TAG, "Failed to retrieve TextView#$methodName() method", ex)
      return null
    }
  }

  private companion object {
    private const val TAG = "ACTVAutoSizeHelper"
    private val tempRectF = RectF()

    // Default minimum size for auto-sizing text in scaled pixels.
    private const val DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12
    // Default maximum size for auto-sizing text in scaled pixels.
    private const val DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112
    // Default value for the step size in pixels.
    private const val DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1

    // Cache of TextView methods used via reflection; the key is the method name and the value is
    // the method itself or null if it can not be found.
    private val textViewMethodByNameCache = ConcurrentHashMap<String, Method>()

    // Use this to specify that any of the auto-size configuration int values have not been set.
    private const val UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1f

    // Ported from TextView#VERY_WIDE. Represents a maximum width in pixels the TextView takes when
    // horizontal scrolling is activated.
    private const val VERY_WIDE = 1024 * 1024
  }
}
