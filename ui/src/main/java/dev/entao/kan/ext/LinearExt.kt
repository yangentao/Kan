@file:Suppress("unused")

package dev.entao.kan.ext

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import dev.entao.kan.appbase.Bmp
import dev.entao.kan.appbase.dp
import dev.entao.kan.appbase.drawable
import dev.entao.kan.base.ColorX

/**
 * Created by entaoyang@163.com on 16/3/12.
 */

class Divider(var color: Int = ColorX.lineGray) {
    //px
    var size: Int = 1
    var begin: Boolean = false
    var mid: Boolean = true
    var end: Boolean = false
    var pad: Int = 0

    fun size(n: Int): Divider {
        this.size = n
        return this
    }

    fun color(color: Int): Divider {
        this.color = color
        return this
    }

    fun begin(b: Boolean = true): Divider {
        this.begin = b
        return this
    }

    fun mid(b: Boolean = true): Divider {
        this.mid = b
        return this
    }

    fun end(b: Boolean = true): Divider {
        this.end = b
        return this
    }

    fun pad(n: Int): Divider {
        this.pad = n
        return this
    }

}

fun <T : LinearLayout> T.divider(): T {
    return this.divider(Divider(ColorX.lineGray))
}

fun <T : LinearLayout> T.divider(color: Int): T {
    return this.divider(Divider(color))
}

fun <T : LinearLayout> T.divider(block: Divider.() -> Unit): T {
    val ld = Divider()
    ld.block()
    return this.divider(ld)
}

fun <T : LinearLayout> T.divider(ld: Divider): T {
    if (ld.size > 0) {
        val d = Bmp.line(ld.size, ld.size, ld.color).drawable
        this.dividerDrawable = d
        this.dividerPadding = ld.pad.dp
        var n = LinearLayout.SHOW_DIVIDER_NONE
        if (ld.begin) {
            n = n or LinearLayout.SHOW_DIVIDER_BEGINNING
        }
        if (ld.mid) {
            n = n or LinearLayout.SHOW_DIVIDER_MIDDLE
        }
        if (ld.end) {
            n = n or LinearLayout.SHOW_DIVIDER_END
        }
        this.showDividers = n
    }
    return this
}

fun <T : LinearLayout> T.hideDivider(): T {
    this.showDividers = LinearLayout.SHOW_DIVIDER_NONE
    return this
}


fun <T : LinearLayout> T.orientationVertical(): T {
    this.orientation = LinearLayout.VERTICAL
    return this
}

fun <T : LinearLayout> T.orientationHorizontal(): T {
    this.orientation = LinearLayout.HORIZONTAL
    return this
}

fun <T : LinearLayout> T.horizontal(): T {
    this.orientation = LinearLayout.HORIZONTAL
    return this
}

fun <T : LinearLayout> T.vertical(): T {
    this.orientation = LinearLayout.VERTICAL
    return this
}

fun <T : LinearLayout> T.isVertical(): Boolean {
    return this.orientation == LinearLayout.VERTICAL
}

fun <T : LinearLayout> T.gravity(n: Int): T {
    this.gravity = n
    return this
}

fun <T : LinearLayout> T.gravityCenterVertical(): T {
    this.gravity = Gravity.CENTER_VERTICAL
    return this
}

fun <T : LinearLayout> T.gravityCenterHorizontal(): T {
    this.gravity = Gravity.CENTER_HORIZONTAL
    return this
}

@SuppressLint("RtlHardcoded")
fun <T : LinearLayout> T.gravityLeftCenter(): T {
    this.gravity = Gravity.LEFT or Gravity.CENTER
    return this
}

@SuppressLint("RtlHardcoded")
fun <T : LinearLayout> T.gravityRightCenter(): T {
    this.gravity = Gravity.RIGHT or Gravity.CENTER
    return this
}

fun <T : LinearLayout> T.gravityCenter(): T {
    this.gravity = Gravity.CENTER
    return this
}


fun <T : LinearLayout> T.addGrayLine(size: Int = 1, margin: Int = 0, color: Int = ColorX.lineGray): View {
    return addGrayLine {
        this.size = size
        this.marginLeft = margin
        this.marginRight = margin
        this.color = color
    }
}

fun <T : LinearLayout> T.addGrayLine(config: GrayLineConfig): View {
    val view = View(context).needId().backColor(config.color)
    if (this.isVertical()) {
        addView(view, LParam.FillW.height(config.size).margins(config.marginLeft, config.marginTop, config.marginRight, config.marginBottom))
    } else {
        addView(view, LParam.FillH.width(config.size).margins(config.marginLeft, config.marginTop, config.marginRight, config.marginBottom))
    }
    return view
}

fun <T : LinearLayout> T.addGrayLine(block: GrayLineConfig.() -> Unit): View {
    val config = GrayLineConfig()
    config.block()
    return addGrayLine(config)
}


fun LinearLayout.addFlex(weight: Double = 1.0): View {
    val view = View(this.context).needId().invisiable()
    if (this.isVertical()) {
        this.addView(view, LParam.FillW.height(0).weight(weight))
    } else {
        this.addView(view, LParam.FillH.width(0).weight(weight))
    }
    return view
}
