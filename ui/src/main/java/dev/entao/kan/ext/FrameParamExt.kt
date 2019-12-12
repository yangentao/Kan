@file:Suppress("unused")

package dev.entao.kan.ext

import android.view.Gravity
import android.widget.FrameLayout

/**
 * Created by entaoyang@163.com on 2016-10-29.
 */


val FParam: FrameLayout.LayoutParams
	get() {
		return FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
	}


val <T : FrameLayout.LayoutParams> T.Top: T
	get() {
		gravity = Gravity.TOP
		return this
	}


val <T : FrameLayout.LayoutParams> T.TopCenter: T
	get() {
		gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
		return this
	}

val <T : FrameLayout.LayoutParams> T.TopLeft: T
	get() {
		gravity = Gravity.TOP or Gravity.LEFT
		return this
	}
val <T : FrameLayout.LayoutParams> T.TopRight: T
	get() {
		gravity = Gravity.TOP or Gravity.RIGHT
		return this
	}
val <T : FrameLayout.LayoutParams> T.Bottom: T
	get() {
		gravity = Gravity.BOTTOM
		return this
	}


val <T : FrameLayout.LayoutParams> T.BottomCenter: T
	get() {
		gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
		return this
	}


val <T : FrameLayout.LayoutParams> T.BottomLeft: T
	get() {
		gravity = Gravity.BOTTOM or Gravity.LEFT
		return this
	}

val <T : FrameLayout.LayoutParams> T.BottomRight: T
	get() {
		gravity = Gravity.BOTTOM or Gravity.RIGHT
		return this
	}

val <T : FrameLayout.LayoutParams> T.Left: T
	get() {
		gravity = Gravity.LEFT
		return this
	}

val <T : FrameLayout.LayoutParams> T.LeftCenter: T
	get() {
		gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
		return this
	}

val <T : FrameLayout.LayoutParams> T.Right: T
	get() {
		gravity = Gravity.RIGHT
		return this
	}

val <T : FrameLayout.LayoutParams> T.RightCenter: T
	get() {
		gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
		return this
	}

val <T : FrameLayout.LayoutParams> T.FillXY: T
	get() {
		gravity = Gravity.FILL
		return this
	}

val <T : FrameLayout.LayoutParams> T.FillY: T
	get() {
		gravity = Gravity.FILL_VERTICAL
		return this
	}

val <T : FrameLayout.LayoutParams> T.FillX: T
	get() {
		gravity = Gravity.FILL_HORIZONTAL
		return this
	}

val <T : FrameLayout.LayoutParams> T.CenterY: T
	get() {
		gravity = Gravity.CENTER_VERTICAL
		return this
	}

val <T : FrameLayout.LayoutParams> T.CenterX: T
	get() {
		gravity = Gravity.CENTER_HORIZONTAL
		return this
	}

val <T : FrameLayout.LayoutParams> T.Center: T
	get() {
		gravity = Gravity.CENTER
		return this
	}


fun <T : FrameLayout.LayoutParams> T.gravity(g: Int): T {
	gravity = g
	return this
}
