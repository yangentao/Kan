@file:Suppress("unused")

package dev.entao.kan.ext

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.DrawableRes
import dev.entao.kan.appbase.*
import dev.entao.kan.base.ColorX
import kotlin.reflect.KClass

/**
 * Created by entaoyang@163.com on 16/3/12.
 */


fun View.removeFromParent() {
    this.parentGroup?.removeView(this)
}

val View.parentGroup: ViewGroup? get() = this.parent as? ViewGroup

val View.asTextView: TextView
    get() {
        return this as TextView
    }
val View.asButton: Button
    get() {
        return this as Button
    }

fun View.child(n: Int): View? {
    val g = this as? ViewGroup ?: return null
    if (g.childCount > n) {
        return g.getChildAt(n)
    }
    return null
}

@Suppress("UNCHECKED_CAST")
fun <T : View> View.child(cls: KClass<T>): T? {
    val g = this as? ViewGroup ?: return null
    for (c in g.childViews) {
        if (c::class == cls) {
            return c as T
        }
    }
    return null
}


fun <T : View> T.genId(): T {
    return this.needId()
}

fun <T : View> T.needId(): T {
    if (this.id == View.NO_ID) {
        this.id = View.generateViewId()
    }
    return this
}

fun <T : View> T.requireId(): Int {
    if (this.id == View.NO_ID) {
        this.id = View.generateViewId()
    }
    return this.id
}

fun <T : View> T.gone(): T {
    visibility = View.GONE
    return this
}

fun <T : View> T.visiable(): T {
    visibility = View.VISIBLE
    return this
}

fun <T : View> T.invisiable(): T {
    visibility = View.INVISIBLE
    return this
}

fun <T : View> T.isGone(): Boolean {
    return visibility == View.GONE
}

fun <T : View> T.isVisiable(): Boolean {
    return visibility == View.VISIBLE
}

fun <T : View> T.isInvisiable(): Boolean {
    return visibility == View.INVISIBLE
}

fun <T : View> T.paddingX(p: Int): T {
    this.setPadding(p.dp, this.paddingTop, p.dp, this.paddingBottom)
    return this
}

fun <T : View> T.paddingY(p: Int): T {
    this.setPadding(this.paddingLeft, p.dp, this.paddingRight, p.dp)
    return this
}

fun <T : View> T.padding(left: Int, top: Int, right: Int, bottom: Int): T {
    this.setPadding(left.dp, top.dp, right.dp, bottom.dp)
    return this
}

fun <T : View> T.padding(p: Int): T {
    this.setPadding(p.dp, p.dp, p.dp, p.dp)
    return this
}


fun <T : View> T.padLeft(n: Int): T {
    this.setPadding(n.dp, this.paddingTop, this.paddingRight, this.paddingBottom)
    return this
}

fun <T : View> T.padTop(n: Int): T {
    this.setPadding(this.paddingLeft, n.dp, this.paddingRight, this.paddingBottom)
    return this
}

fun <T : View> T.padRight(n: Int): T {
    this.setPadding(this.paddingLeft, this.paddingTop, n.dp, this.paddingBottom)
    return this
}

fun <T : View> T.padBottom(n: Int): T {
    this.setPadding(this.paddingLeft, this.paddingTop, this.paddingRight, n.dp)
    return this
}

fun <T : View> T.backColor(color: Int): T {
    setBackgroundColor(color)
    return this
}

fun <T : View> T.backColor(color: Int, fadeColor: Int): T {
    backColorList(color) {
        lighted(fadeColor)
    }
    return this
}


fun <T : View> T.backColorWhite(): T {
    setBackgroundColor(Color.WHITE)
    return this
}

fun <T : View> T.backColorTrans(): T {
    setBackgroundColor(ColorX.TRANS)
    return this
}

fun <T : View> T.backColorTheme(): T {
    backColor(ColorX.theme)
    return this
}

fun <T : View> T.backColorThemeFade(): T {
    backColor(ColorX.theme, ColorX.fade)
    return this
}

fun <T : View> T.backColorWhiteFade(): T {
    backColor(Color.WHITE, ColorX.fade)
    return this
}

fun <T : View> T.backColorTransFade(): T {
    backColor(ColorX.TRANS, ColorX.fade)
    return this
}

fun <T : View> T.backColorPage(): T {
    setBackgroundColor(ColorX.backGray)
    return this
}

fun <T : View> T.backFillFade(fillColor: Int, corner: Int): T {
    val a = ShapeRect(fillColor, corner).value
    val b = ShapeRect(ColorX.fade, corner).value
    backDrawable(a, b)
    return this
}


fun <T : View> T.backRes(@DrawableRes resId: Int): T {
    this.setBackgroundResource(resId)
    return this
}

fun <T : View> T.backDrawable(d: Drawable): T {
    this.background = d
    return this
}


fun <T : View> T.backDrawable(normal: Drawable, pressed: Drawable): T {
    backDrawableList(normal) {
        lighted(pressed)
    }
    return this
}

fun <T : View> T.backDrawable(@DrawableRes resId: Int, @DrawableRes pressed: Int): T {
    backResList(resId) {
        lighted(pressed)
    }
    return this
}

fun <T : View> T.backRect(block: ShapeRect.() -> Unit): T {
    val a = ShapeRect()
    a.block()
    this.background = a.value
    return this
}


fun View.clickable(): View {
    this.isClickable = true
    return this
}




