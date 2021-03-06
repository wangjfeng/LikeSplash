package com.github.jasonhezz.likesplash.view

/**
 * Created by JakeWharton
 * https://gist.github.com/JakeWharton/2856179
 */

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.github.jasonhezz.likesplash.R

/** Maintains an aspect ratio based on either width or height. Disabled by default.   */
class AspectRatioImageView @JvmOverloads constructor(context: Context,
    attrs: AttributeSet? = null) : AppCompatImageView(context, attrs) {

  var aspectRatio = 0f
    /** Set the aspect ratio for this image view. This will update the view instantly.  */
    set(value) {
      field = value
      if (aspectRatioEnabled) requestLayout()
    }

  var aspectRatioEnabled: Boolean = false
    set(value) {
      field = value
      requestLayout()
    }

  var dominantMeasurement = MEASUREMENT_WIDTH
    set(value) {
      if (value != MEASUREMENT_HEIGHT && value != MEASUREMENT_WIDTH) {
        throw IllegalArgumentException("Invalid measurement type.")
      }
      field = value
      requestLayout()
    }

  init {

    val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
    aspectRatio = a.getFloat(R.styleable.AspectRatioImageView_aspectRatio, DEFAULT_ASPECT_RATIO)
    aspectRatioEnabled = a.getBoolean(R.styleable.AspectRatioImageView_aspectRatioEnabled,
        DEFAULT_ASPECT_RATIO_ENABLED)
    dominantMeasurement = a.getInt(R.styleable.AspectRatioImageView_dominantMeasurement,
        DEFAULT_DOMINANT_MEASUREMENT)
    a.recycle()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    if (!aspectRatioEnabled) return

    val newWidth: Int
    val newHeight: Int
    when (dominantMeasurement) {
      MEASUREMENT_WIDTH -> {
        newWidth = measuredWidth
        newHeight = (newWidth * aspectRatio).toInt()
      }

      MEASUREMENT_HEIGHT -> {
        newHeight = measuredHeight
        newWidth = (newHeight * aspectRatio).toInt()
      }

      else -> throw IllegalStateException("Unknown measurement with ID " + dominantMeasurement)
    }

    setMeasuredDimension(newWidth, newHeight)
  }

  companion object {
    // NOTE: These must be kept in sync with the AspectRatioImageView attributes in attrs.xml.
    val MEASUREMENT_WIDTH = 0
    val MEASUREMENT_HEIGHT = 1

    private val DEFAULT_ASPECT_RATIO = 1f
    private val DEFAULT_ASPECT_RATIO_ENABLED = false
    private val DEFAULT_DOMINANT_MEASUREMENT = MEASUREMENT_WIDTH
  }
}
