@file:Suppress("unused")

package dev.entao.kan.ext

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.LinearLayout

/**
 * Created by entaoyang@163.com on 2016-07-21.
 */


val LParam: LinearLayout.LayoutParams
    get() {
        return LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }


val <T : LinearLayout.LayoutParams> T.FlexX: T
    get() {
        return this.WidthFlex
    }
val <T : LinearLayout.LayoutParams> T.WidthFlex: T
    get() {
        return this.width(0).weight(1)
    }

val <T : LinearLayout.LayoutParams> T.FlexY: T
    get() {
        return this.HeightFlex
    }
val <T : LinearLayout.LayoutParams> T.HeightFlex: T
    get() {
        return this.height(0).weight(1)
    }


fun <T : LinearLayout.LayoutParams> T.weight(w: Int): T {
    return weight(w.toFloat())
}

fun <T : LinearLayout.LayoutParams> T.weight(w: Double): T {
    return weight(w.toFloat())
}

fun <T : LinearLayout.LayoutParams> T.weight(w: Float): T {
    weight = w
    return this
}

val <T : LinearLayout.LayoutParams> T.Top: T
    get() {
        gravity = Gravity.TOP
        return this
    }


val <T : LinearLayout.LayoutParams> T.TopCenter: T
    get() {
        gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        return this
    }

val <T : LinearLayout.LayoutParams> T.Bottom: T
    get() {
        gravity = Gravity.BOTTOM
        return this
    }


val <T : LinearLayout.LayoutParams> T.BottomCenter: T
    get() {
        gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        return this
    }


val <T : LinearLayout.LayoutParams> T.Left: T
    @SuppressLint("RtlHardcoded")
    get() {
        gravity = Gravity.LEFT
        return this
    }


val <T : LinearLayout.LayoutParams> T.LeftCenter: T
    @SuppressLint("RtlHardcoded")
    get() {
        gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
        return this
    }

val <T : LinearLayout.LayoutParams> T.Right: T
    @SuppressLint("RtlHardcoded")
    get() {
        gravity = Gravity.RIGHT
        return this
    }


val <T : LinearLayout.LayoutParams> T.RightCenter: T
    @SuppressLint("RtlHardcoded")
    get() {
        gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        return this
    }


val <T : LinearLayout.LayoutParams> T.GravityFill: T
    get() {
        gravity = Gravity.FILL
        return this
    }


val <T : LinearLayout.LayoutParams> T.FillVertical: T
    get() {
        gravity = Gravity.FILL_VERTICAL
        return this
    }


val <T : LinearLayout.LayoutParams> T.FillHorizontal: T
    get() {
        gravity = Gravity.FILL_HORIZONTAL
        return this
    }


val <T : LinearLayout.LayoutParams> T.CenterVertical: T
    get() {
        gravity = Gravity.CENTER_VERTICAL
        return this
    }


val <T : LinearLayout.LayoutParams> T.CenterHorizontal: T
    get() {
        gravity = Gravity.CENTER_HORIZONTAL
        return this
    }


val <T : LinearLayout.LayoutParams> T.Center: T
    get() {
        gravity = Gravity.CENTER
        return this
    }

