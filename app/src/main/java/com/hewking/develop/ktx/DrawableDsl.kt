package com.hewking.develop.ktx

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.Log
import androidx.annotation.*
import androidx.core.graphics.drawable.DrawableCompat
import java.lang.reflect.Field
import kotlin.math.roundToInt

/**
 * @Description:
 * Drawable DSL 扩展, 基于[GradientDrawable]实现.
 * 部分属性在特定版本使用了反射实现. 例如: [applyPadding] [applyRing]
 * @see [GradientDrawable]
 * @see [DrawableCompat]
 * @Author: jianhao
 * @Date:   2021/4/22 13:51
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
object DrawableDsl {

  /**
   * DrawableInflater, 用于填充drawable.xml
   * @see [DrawableDsl.createDrawable]
   */
  interface DrawableInflater {
    fun inflate(@DrawableRes resId: Int): Drawable?
  }

//  var drawableInflater: DrawableInflater? = DrawableInflaterInternal()
//
//  internal class DrawableInflaterInternal : DrawableInflater {
//    override fun inflate(@DrawableRes resId: Int): Drawable? {
//      // 替换为自己的getDrawable逻辑
//      val context = ContextGetter.getUnsafe() ?: return null
//      return ContextCompat.getDrawable(context, resId)
//    }
//  }

  @IntDef(flag = true, value = [RECTANGLE, OVAL, LINE, RING])
  @Retention(AnnotationRetention.SOURCE)
  annotation class Shape

  /**
   * 矩形
   * @see [GradientDrawable.RECTANGLE]
   */
  const val RECTANGLE = GradientDrawable.RECTANGLE

  /**
   * 圆形
   * @see [GradientDrawable.OVAL]
   */
  const val OVAL = GradientDrawable.OVAL

  /**
   * 线
   * @see [GradientDrawable.LINE]
   */
  const val LINE = GradientDrawable.LINE

  /**
   * 圆环
   * @see [GradientDrawable.RING]
   */
  @Deprecated("不推荐使用，无效")
  const val RING = GradientDrawable.RING

  /**
   * 线性渐变, 可以改变渐变角度, 步长45度
   * @see [GradientDrawable.LINEAR_GRADIENT]
   * @see [GradientDrawable.setOrientation]
   */
  const val GRADIENT_LINEAR = GradientDrawable.LINEAR_GRADIENT

  /**
   * 半径渐变, 可以改变渐变半径
   * @see [GradientDrawable.RADIAL_GRADIENT]
   * @see [GradientDrawable.setGradientRadius]
   */
  const val GRADIENT_RADIAL = GradientDrawable.RADIAL_GRADIENT

  /**
   * 扫描渐变, 半径和角度属性无效
   * @see [GradientDrawable.SWEEP_GRADIENT]
   */
  const val GRADIENT_SWEEP = GradientDrawable.SWEEP_GRADIENT

  @IntDef(flag = true, value = [GRADIENT_LINEAR, GRADIENT_RADIAL, GRADIENT_SWEEP])
  @Retention(AnnotationRetention.SOURCE)
  annotation class Gradient

  /**
   * 基础样式
   *
   * 属性介绍 [drawable]
   */
  class Basic @JvmOverloads constructor(
      @Shape
      var shape: Int,
      @androidx.annotation.IntRange(from = 0L, to = 255L)
      var alpha: Int = 255,
      var ring: Ring? = null,
      var tint: Tint? = null,
      var dither: Boolean = false,
  ) {

    /**
     * 圆环属性
     * @see [drawable]
     */
    class Ring(
        @Px
        val innerRadius: Float,
        val innerRadiusRatio: Float,

        @Px
        val thickness: Float,
        val thicknessRatio: Float,
        /**
         * 当前属性在Android 9.0以后属于黑名单, 无法访问
         */
        val useLevelForShape: Boolean
    )

    /**
     * tint属性
     * @see [drawable]
     */
    class Tint(
        @ColorInt
        val tintColor: Int,
        val tintMode: PorterDuff.Mode
    )
  }


  /**
   * 子属性
   *
   * @see [size]
   * @see [gradient]
   * @see [padding]
   * @see [corners]
   * @see [color]
   * @see [stroke]
   */
  class DrawableConfiguration @JvmOverloads constructor(
      var color: Int = 0,
      var corners: Corners? = null,
      var size: Size? = null,
      var stroke: Stroke? = null,
      var padding: RectF? = null,
      var gradient: Gradient? = null
  ) {

    /**
     * 描边
     * @see [stroke]
     */
    class Stroke(
        val color: Int, val width: Float,
        val dashWidth: Float, val dashGap: Float
    )

    /**
     * 大小
     * @see [size]
     */
    class Size(val width: Float, val height: Float)

    /**
     * 圆角
     * @see [corners]
     */
    class Corners(
        val topLeft: Float,
        val topRight: Float,
        val bottomRight: Float,
        val bottomLeft: Float,
        val radius: Float
    )

    /**
     * 渐变色
     * @see [gradient]
     */
    class Gradient(
        val type: Int,
        val startColor: Int,
        val centerColor: Int,
        val endColor: Int,
        val centerX: Float,
        val centerY: Float,
        /**
         * 0-360, 步长45
         * 映射关系查看[GradientDrawable.updateGradientDrawableGradient]
         */
        val angle: Int,
        val gradientRadius: Float,
        val useLevel: Boolean
    )

    /**
     * 描边
     * 对应<stroke/>
     *
     * [color] 描边颜色
     * [width] 描边宽度
     * [dashWidth] 描边虚线宽度
     * [dashGap] 描边虚线空白宽度
     *
     * @see [GradientDrawable.setStroke]
     */
    fun stroke(
        @ColorInt
        color: Int = 0,
        @Px
        width: Float = 0f,
        @Px
        dashWidth: Float = 0f,
        @Px
        dashGap: Float = 0f
    ) {
      stroke = Stroke(color, width, dashWidth, dashGap)
    }

    /**
     * 渐变色
     * 对应<gradient/>
     *
     * [type] 渐变类型 [DrawableDsl.GRADIENT_LINEAR] [DrawableDsl.GRADIENT_RADIAL] [DrawableDsl.GRADIENT_SWEEP]
     * [startColor] 开始颜色
     * [centerColor] 中间颜色
     * [endColor] 结束颜色
     * [centerX] 渐变圆心位置x轴比例
     * [centerY] 渐变圆心位置y轴比例
     * [angle] 旋转角度, 步长45, 只有在[DrawableDsl.GRADIENT_LINEAR]时可用. [GradientDrawable.setOrientation]
     * [gradientRadius] 渐变半径, 只有在[DrawableDsl.GRADIENT_RADIAL]时可用
     * [useLevel] [GradientDrawable.setUseLevel]
     *
     * @see [GradientDrawable.setGradientType]
     * @see [GradientDrawable.setColors]
     * @see [GradientDrawable.setGradientCenter]
     * @see [GradientDrawable.setOrientation]
     * @see [GradientDrawable.setGradientRadius]
     * @see [GradientDrawable.setUseLevel]
     */
    @SuppressLint("Range")
    fun gradient(
        @DrawableDsl.Gradient
        type: Int,

        @ColorInt
        startColor: Int,
        @ColorInt
        centerColor: Int = -1,
        @ColorInt
        endColor: Int,

        @FloatRange(from = 0.0, to = 1.0)
        centerX: Float = 0.5f,
        @FloatRange(from = 0.0, to = 1.0)
        centerY: Float = 0.5f,

        @androidx.annotation.IntRange(from = 0, to = 360)
        angle: Int = -1,
        /**
         * 渐变半径, 只有在[DrawableDsl.GRADIENT_RADIAL]时可用
         */
        @Px
        gradientRadius: Float = 0f,

        useLevel: Boolean = false
    ) {
      gradient = Gradient(
          type, startColor, centerColor, endColor, centerX, centerY,
          angle, gradientRadius, useLevel
      )
    }

    /**
     * 圆角
     * 对应<corners/>
     * [topLeft] 左上角
     * [topRight] 右上角
     * [bottomRight] 右下角
     * [bottomLeft] 左下角
     * [radius] 所有边角, 优先级最高
     *
     * @see [GradientDrawable.setCornerRadius]
     * @see [GradientDrawable.setCornerRadii]
     */
    fun corners(
        @Px
        topLeft: Float = 0f,
        @Px
        topRight: Float = 0f,
        @Px
        bottomRight: Float = 0f,
        @Px
        bottomLeft: Float = 0f,

        @Px
        radius: Float = 0f
    ) {
      corners = Corners(topLeft, topRight, bottomRight, bottomLeft, radius)
    }

    /**
     * <size/>
     *
     * [width] 宽度
     * [height] 高度
     * [all] 正方形, 优先级最高
     * @see [GradientDrawable.setSize]
     */
    fun size(
        @Px
        width: Float = 0f,
        @Px
        height: Float = 0f,
        @Px
        all: Float = 0f,
    ) {
      size = if (all > 0f) {
        Size(all, all)
      } else {
        Size(width, height)
      }
    }

    /**
     * 填充色
     * <solid/>
     *
     * [color] 填充颜色值
     * @see [GradientDrawable.setColor]
     */
    fun color(@ColorInt color: Int) {
      this.color = color
    }

    /**
     * 内边距
     * <padding/>
     *
     * [left] 左边距
     * [top] 上边距
     * [right] 右边距
     * [bottom] 下边距
     * [all] 所有边距, 优先级最高
     * @see [GradientDrawable.setPadding]
     */
    fun padding(
        @Px
        left: Float = 0f,
        @Px
        top: Float = 0f,
        @Px
        right: Float = 0f,
        @Px
        bottom: Float = 0f,
        @Px
        all: Float = 0f
    ) {
      padding = if (all > 0f) {
        RectF(all, all, all, all)
      } else {
        RectF(left, top, right, bottom)
      }
    }

  }

  /**
   * 初始化Drawable, 在圆环时使用xml初始化
   */
  private fun createDrawable(): GradientDrawable {
//    val useStandIn = this.shape == RING && this.ring?.useLevelForShape == false
//    var drawable: GradientDrawable? = null
//    if (useStandIn) {
//      val drawableInflater = DrawableDsl.drawableInflater
//      if (drawableInflater == null) {
//        Log.w("DrawableDsl", "drawableInflater == null")
//      }
//      // useLevelForShape=false时使用xml初始化
//      /**
//       * <shape xmlns:android="http://schemas.android.com/apk/res/android"
//       *      android:shape="ring"
//       *      android:useLevel="false" />
//       */
//      val inflate = drawableInflater?.inflate(R.drawable.drawable_ring_stand_in)
//      drawable = inflate as? GradientDrawable
//      if (drawable == null && inflate != null) {
//        Log.w(
//            "DrawableDsl",
//            "inflate drawable instance of: ${inflate.javaClass}"
//        )
//      }
//    }
//    return drawable ?: GradientDrawable()
    return GradientDrawable()
  }


  /**
   * create shape
   *
   * [basic] 基础属性
   * [drawableConfiguration] 子节点属性
   *
   * @see [drawable]
   * @see [Basic]
   * @see [DrawableConfiguration]
   */
  @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
  @JvmStatic
  fun drawable(basic: Basic, drawableConfiguration: DrawableConfiguration): Drawable {
    val drawable = createDrawable()

    applyBasic(drawable, basic)

    drawable.setColor(drawableConfiguration.color)

    drawableConfiguration.corners?.let {
      if (basic.shape == OVAL) {
        Log.w("DrawableDsl", "OVAL: cornerRadius属性无效")
      } else if (basic.shape == LINE) {
        Log.w("DrawableDsl", "LINE: cornerRadius属性无效")
      }
      applyCornerRadius(drawable, it)
    }

    drawableConfiguration.size?.let {
      drawable.setSize(it.width.roundToInt(), it.height.roundToInt())
    }

    drawableConfiguration.padding?.let {
      applyPadding(drawable, it)
    }

    drawableConfiguration.stroke?.let {
      applyStroke(drawable, it)
    }

    drawableConfiguration.gradient?.let {
      if (drawableConfiguration.color != 0) {
        Log.w("DrawableDsl", "gradient属性覆盖了color属性")
      }
      if (basic.shape == LINE) {
        Log.w("DrawableDsl", "LINE: gradient属性无效")
      }
      applyGradient(drawable, it)
    }

    // 兼容Android 5.0以下版本
    basic.tint?.let {
      return wrapTint(drawable, it)
    }

    return drawable
  }

}

/**
 * 代码初始化shape-drawable对象
 *
 * [shape] shape属性 [GradientDrawable.setShape]
 * [alpha] 透明度 [Drawable.setAlpha]
 * [innerRadius] 圆环环内半径 [GradientDrawable.setInnerRadius]
 * [innerRadiusRatio] 当innerRadius == -1时, 以drawable的bounds宽度比率来表示内环半径 [GradientDrawable.setInnerRadiusRatio]
 * [thickness] 圆环的厚度 [GradientDrawable.setThickness]
 * [thicknessRatio] 当thickness == -1时, 以drawable的bounds宽度比率来表示环厚度 [GradientDrawable.setThicknessRatio]
 * [useLevelForShape] 默认为true, 功能类似[GradientDrawable.setUseLevel]
 *
 * [tintColor] tint颜色 [DrawableCompat.setTint]
 * [tintMode] tint模式 [DrawableCompat.setTintMode] [PorterDuff.Mode]
 * [dither] dither属性 [GradientDrawable.setDither]
 *
 * [_initialization] 初始化其他子属性 [DrawableDsl.DrawableConfiguration]
 *
 * @see [size]
 * @see [gradient]
 * @see [padding]
 * @see [corners]
 * @see [color]
 * @see [stroke]
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun drawable(
    @DrawableDsl.Shape
    shape: Int = DrawableDsl.RECTANGLE,

    @androidx.annotation.IntRange(from = 0L, to = 255L)
    alpha: Int = 255,

    @Px
    innerRadius: Float = -1f,
    innerRadiusRatio: Float = 3f,
    @Px
    thickness: Float = -1f,
    thicknessRatio: Float = 9f,
    useLevelForShape: Boolean = true,

    @ColorInt
    tintColor: Int = 0,
    tintMode: PorterDuff.Mode? = null,

    dither: Boolean = false,

    _initialization: DrawableDsl.DrawableConfiguration.() -> Unit
): Drawable {
  val ring = DrawableDsl.Basic.Ring(
      innerRadius,
      innerRadiusRatio,
      thickness,
      thicknessRatio,
      useLevelForShape
  )
  val tint = if (tintColor == 0 || tintMode == null) {
    null
  } else {
    DrawableDsl.Basic.Tint(tintColor, tintMode)
  }
  val basic = DrawableDsl.Basic(shape, alpha, ring, tint, dither)
  val drawableParams = DrawableDsl.DrawableConfiguration().apply(_initialization)
  return DrawableDsl.drawable(basic, drawableParams)
}


/**
 * tint属性
 */
private fun wrapTint(drawable: GradientDrawable, tint: DrawableDsl.Basic.Tint): Drawable {
  val wrapDrawable = DrawableCompat.wrap(drawable)
  DrawableCompat.setTint(wrapDrawable, tint.tintColor)
  DrawableCompat.setTintMode(wrapDrawable, tint.tintMode)
  return wrapDrawable
}

/**
 * gradient属性
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
private fun applyGradient(drawable: GradientDrawable, gradient: DrawableDsl.DrawableConfiguration.Gradient) {
  drawable.useLevel = gradient.useLevel
  drawable.setGradientCenter(gradient.centerX, gradient.centerY)
  if (gradient.centerColor < 0) {
    drawable.colors = intArrayOf(gradient.startColor, gradient.endColor)
  } else {
    drawable.colors =
        intArrayOf(gradient.startColor, gradient.centerColor, gradient.endColor)
  }

  val hasAngle = gradient.angle != -1 && gradient.angle >= 0
  var orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM
  if (hasAngle) {
    val angle = 45 * (gradient.angle % 360 / 45)
    val out = gradient.angle % 45
    if (out > 0) {
      Log.w("DrawableDsl", "gradient angle属性错误, 丢弃多余角度: $out")
    }
    orientation = when (angle) {
      0 -> GradientDrawable.Orientation.LEFT_RIGHT
      45 -> GradientDrawable.Orientation.BL_TR
      90 -> GradientDrawable.Orientation.BOTTOM_TOP
      135 -> GradientDrawable.Orientation.BR_TL
      180 -> GradientDrawable.Orientation.RIGHT_LEFT
      225 -> GradientDrawable.Orientation.TR_BL
      270 -> GradientDrawable.Orientation.TOP_BOTTOM
      315 -> GradientDrawable.Orientation.TL_BR
      else -> GradientDrawable.Orientation.TOP_BOTTOM
    }
  }
  drawable.gradientType = gradient.type
  when (gradient.type) {
    GradientDrawable.LINEAR_GRADIENT -> {
      if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
        drawable.orientation = orientation
      }
      if (gradient.gradientRadius != 0f) {
        Log.w("DrawableDsl", "LINEAR_GRADIENT: gradientRadius属性无效")
      }
    }
    GradientDrawable.RADIAL_GRADIENT -> {
      drawable.gradientRadius = gradient.gradientRadius
      if (hasAngle) {
        Log.w("DrawableDsl", "RADIAL_GRADIENT: orientation属性无效")
      }
    }
    GradientDrawable.SWEEP_GRADIENT -> {
      // 扫描类型不解析这些属性
      if (hasAngle) {
        Log.w("DrawableDsl", "SWEEP_GRADIENT: orientation属性无效")
      }
      if (gradient.gradientRadius != 0f) {
        Log.w("DrawableDsl", "SWEEP_GRADIENT: gradientRadius属性无效")
      }
    }
  }
}

/**
 * stroke属性
 */
private fun applyStroke(drawable: GradientDrawable, stroke: DrawableDsl.DrawableConfiguration.Stroke) {
  if (stroke.dashWidth > 0f && stroke.dashGap > 0f) {
    drawable.setStroke(
        stroke.width.roundToInt(),
        stroke.color,
        stroke.dashWidth,
        stroke.dashGap
    )
  } else {
    drawable.setStroke(stroke.width.roundToInt(), stroke.color)
  }
}

/**
 * corner属性
 * [GradientDrawable.updateDrawableCorners]
 */
private fun applyCornerRadius(
    drawable: GradientDrawable,
    corners: DrawableDsl.DrawableConfiguration.Corners
) {
  if (corners.radius > 0) {
    drawable.cornerRadius = corners.radius
  } else {
    drawable.cornerRadii = floatArrayOf(
        corners.topLeft, corners.topLeft,
        corners.topRight, corners.topRight,
        corners.bottomRight, corners.bottomRight,
        corners.bottomLeft, corners.bottomLeft
    )
  }
}

/**
 * 应用基础属性
 */
private fun applyBasic(drawable: GradientDrawable, basic: DrawableDsl.Basic) {
  drawable.shape = basic.shape
  if (basic.shape == DrawableDsl.RING) {
    if (basic.ring == null) {
      Log.w("DrawableDsl", "未配置ring属性")
    }
    basic.ring?.let {
      applyRing(drawable, it)
    }
  }

  drawable.setDither(basic.dither)
  drawable.alpha = basic.alpha
}

/**
 * ring属性
 * 使用xml替身兼容全版本
 * @see [DrawableDsl.createDrawable]
 * [GradientDrawable.updateStateFromTypedArray]
 */
@SuppressLint("PrivateApi")
private fun applyRing(drawable: GradientDrawable, ring: DrawableDsl.Basic.Ring) {

  /**
   * Android 10(29)及以上有Api调用
   */
  @RequiresApi(Build.VERSION_CODES.Q)
  fun applyRingApi29() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      drawable.innerRadius = ring.innerRadius.roundToInt()
      if (ring.innerRadius == -1f) {
        drawable.innerRadiusRatio = ring.innerRadiusRatio
      }

      drawable.thickness = ring.thickness.roundToInt()
      if (ring.thickness == -1f) {
        drawable.thicknessRatio = ring.thicknessRatio
      }
    }
  }

  /**
   * Android 10以下版本走反射, 且不在Android 9黑名单
   */
  fun applyRingByReflex() {
    try {
      val gradientStateClass = Class.forName(CLASS_GRADIENT_STATE)

      val mInnerRadiusField = gradientStateClass.declaredField("mInnerRadius")
      mInnerRadiusField.setInt(drawable.constantState, ring.innerRadius.roundToInt())
      if (ring.innerRadius == -1f) {
        val mInnerRadiusRatioField = gradientStateClass.declaredField("mInnerRadiusRatio")
        mInnerRadiusRatioField.setFloat(drawable.constantState, ring.innerRadiusRatio)
      }

      val mThicknessField = gradientStateClass.declaredField("mThickness")
      mThicknessField.setInt(drawable.constantState, ring.thickness.roundToInt())
      if (ring.thickness == -1f) {
        val mThicknessRatioField = gradientStateClass.declaredField("mThicknessRatio")
        mThicknessRatioField.setFloat(drawable.constantState, ring.innerRadiusRatio)
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    applyRingApi29()
  } else {
    applyRingByReflex()
  }
}

private const val CLASS_GRADIENT_STATE =
    "android.graphics.drawable.GradientDrawable\$GradientState"

/**
 * padding属性, 全版本兼容
 */
@SuppressLint("PrivateApi")
private fun applyPadding(drawable: GradientDrawable, padding: RectF) {

  /**
   * Android 10(29)及以上有Api调用
   */
  @RequiresApi(Build.VERSION_CODES.Q)
  fun applyPaddingApi29() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      drawable.setPadding(
          padding.left.roundToInt(),
          padding.top.roundToInt(),
          padding.right.roundToInt(),
          padding.bottom.roundToInt()
      )
    }
  }

  /**
   * Android 10以下版本走反射, 且不在Android 9黑名单
   */
  fun applyPaddingByReflex() {
    val rect = Rect(
        padding.left.roundToInt(),
        padding.top.roundToInt(),
        padding.right.roundToInt(),
        padding.bottom.roundToInt()
    )

    fun setPadding(obj: Any?, clazz: Class<*>, rect: Rect) {
      try {
        val mPaddingField = clazz.declaredField("mPadding")
        mPaddingField.set(obj, rect)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }

    setPadding(drawable, GradientDrawable::class.java, rect)

    try {
      val gradientStateClass = Class.forName(CLASS_GRADIENT_STATE)
      setPadding(drawable.constantState, gradientStateClass, rect)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    applyPaddingApi29()
  } else {
    applyPaddingByReflex()
  }
}

private fun Class<*>.declaredField(fieldName: String): Field {
  val declaredField = this.getDeclaredField(fieldName)
  declaredField.isAccessible = true
  return declaredField
}
