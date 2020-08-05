@file:Suppress("unused")

package dev.entao.kan.ext

import android.graphics.Color
import android.widget.Switch
import dev.entao.kan.appbase.ShapeRect
import dev.entao.kan.appbase.argb
import dev.entao.kan.appbase.dp
import dev.entao.kan.appbase.listDrawable
import dev.entao.kan.base.ColorX

/**
 * Created by entaoyang@163.com on 16/6/3.
 */


fun <T : Switch> T.themeSwitch(): T {
    this.thumbTextPadding = 10.dp

    val w1 = 30
    val h1 = 30

    val d1 = ShapeRect(0xFFCCCCCCL.argb, h1 / 2).size(w1, h1).value
    val d2 = ShapeRect(0xFF4A90E2L.argb, h1 / 2).size(w1, h1).value
    val d3 = ShapeRect(ColorX.backDisabled, h1 / 2).stroke(1, ColorX.borderGray).size(w1, h1).value
    this.thumbDrawable = listDrawable(d1) {
        checked(d2)
        disabled(d3)
    }

    val w = 50
    val h = 30
    val dd1 = ShapeRect(Color.WHITE, h / 2).stroke(1, ColorX.borderGray).size(w, h).value
    val dd2 = ShapeRect(ColorX.green, h / 2).size(w, h).value
    val dd3 = ShapeRect(ColorX.backDisabled, h / 2).stroke(1, Color.WHITE).size(w, h).value
    this.trackDrawable = listDrawable(dd1) {
        checked(dd2)
        disabled(dd3)
    }
    return this
}
