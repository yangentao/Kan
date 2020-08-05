@file:Suppress("unused")

package dev.entao.kan.appbase

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources


enum class ViewState(val value: Int) {
    Selected(android.R.attr.state_selected), Unselected(-android.R.attr.state_selected),
    Pressed(android.R.attr.state_pressed), Unpressed(-android.R.attr.state_pressed),
    Enabled(android.R.attr.state_enabled), Disabled(-android.R.attr.state_enabled),
    Checked(android.R.attr.state_checked), Unchecked(-android.R.attr.state_checked),
    Focused(android.R.attr.state_focused), Unfocused(-android.R.attr.state_focused),
    Normal(0)
}


class ViewStateBuilder<T>(val normalValue: T) {
    private val items: ArrayList<Pair<Int, T>> = ArrayList()

    val values: ArrayList<Pair<Int, T>>
        get() {
            items += ViewState.Normal.value to normalValue
            return items
        }

    fun lighted(v: T): ViewStateBuilder<T> {
        pressed(v)
        selected(v)
        focused(v)
        return this
    }

    fun selected(v: T): ViewStateBuilder<T> {
        items += ViewState.Selected.value to v
        return this
    }

    fun unselected(v: T): ViewStateBuilder<T> {
        items += ViewState.Unselected.value to v
        return this
    }

    fun pressed(v: T): ViewStateBuilder<T> {
        items += ViewState.Pressed.value to v
        return this
    }

    fun unpressed(v: T): ViewStateBuilder<T> {
        items += ViewState.Unpressed.value to v
        return this
    }

    fun enabled(v: T): ViewStateBuilder<T> {
        items += ViewState.Enabled.value to v
        return this
    }

    fun disabled(v: T): ViewStateBuilder<T> {
        items += ViewState.Disabled.value to v
        return this
    }

    fun checked(v: T): ViewStateBuilder<T> {
        items += ViewState.Checked.value to v
        return this
    }

    fun unchecked(v: T): ViewStateBuilder<T> {
        items += ViewState.Unchecked.value to v
        return this
    }

    fun focused(v: T): ViewStateBuilder<T> {
        items += ViewState.Focused.value to v
        return this
    }

    fun unfocused(v: T): ViewStateBuilder<T> {
        items += ViewState.Unfocused.value to v
        return this
    }
}

val ViewStateBuilder<Int>.colorList: ColorStateList
    get() {
        val a: Array<IntArray> = values.map { intArrayOf(it.first) }.toTypedArray()
        val b: IntArray = values.map { it.second }.toIntArray()
        return ColorStateList(a, b)
    }

val ViewStateBuilder<Int>.colorDrawables: StateListDrawable
    get() {
        val ld = StateListDrawable()
        for (p in values) {
            ld.addState(intArrayOf(p.first), ColorDrawable(p.second))
        }
        return ld
    }

val ViewStateBuilder<Int>.resDrawables: StateListDrawable
    get() {
        val ld = StateListDrawable()
        for (p in values) {
            val d = AppCompatResources.getDrawable(App.inst, p.second)!!
            ld.addState(intArrayOf(p.first), d)
        }
        return ld
    }

val ViewStateBuilder<Drawable>.drawableList: StateListDrawable
    get() {
        val ld = StateListDrawable()
        for (p in values) {
            ld.addState(intArrayOf(p.first), p.second)
        }
        return ld
    }

fun listColor(color: Int, block: ViewStateBuilder<Int>.() -> Unit): ColorStateList {
    val m = ViewStateBuilder<Int>(color)
    m.block()
    return m.colorList
}

fun listColorDrawable(color: Int, block: ViewStateBuilder<Int>.() -> Unit): StateListDrawable {
    val m = ViewStateBuilder<Int>(color)
    m.block()
    return m.colorDrawables
}

fun listRes(normalRes: Int, block: ViewStateBuilder<Int>.() -> Unit): StateListDrawable {
    val m = ViewStateBuilder<Int>(normalRes)
    m.block()
    return m.resDrawables
}

fun listDrawable(normal: Drawable, block: ViewStateBuilder<Drawable>.() -> Unit): StateListDrawable {
    val m = ViewStateBuilder<Drawable>(normal)
    m.block()
    return m.drawableList
}

fun lightColors(normalValue: Int, lightValue: Int): ColorStateList {
    return listColor(normalValue) {
        lighted(lightValue)
    }
}

fun lightColorDrawables(normalValue: Int, lightValue: Int): StateListDrawable {
    return listColorDrawable(normalValue) {
        lighted(lightValue)
    }
}

fun lightResDrawables(normalValue: Int, lightValue: Int): StateListDrawable {
    return listRes(normalValue) {
        lighted(lightValue)
    }
}

fun lightDrawables(normalValue: Drawable, lightValue: Drawable): StateListDrawable {
    return listDrawable(normalValue) {
        lighted(lightValue)
    }
}

fun <T : TextView> T.textColorList(color: Int, block: ViewStateBuilder<Int>.() -> Unit): T {
    val a = listColor(color, block)
    this.setTextColor(a)
    return this
}

fun <T : View> T.backColorList(color: Int, block: ViewStateBuilder<Int>.() -> Unit): T {
    this.background = listColorDrawable(color, block)
    return this
}

fun <T : View> T.backResList(@DrawableRes normalRes: Int, block: ViewStateBuilder<Int>.() -> Unit): T {
    this.background = listRes(normalRes, block)
    return this
}

fun <T : View> T.backDrawableList(normal: Drawable, block: ViewStateBuilder<Drawable>.() -> Unit): T {
    this.background = listDrawable(normal, block)
    return this
}