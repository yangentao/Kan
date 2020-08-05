@file:Suppress("unused")

package dev.entao.kan.ext

import android.view.ViewGroup
import dev.entao.kan.appbase.dp
import dev.entao.kan.theme.ViewSize

/**
 * Created by yangentao on 2015/11/21.
 * entaoyang@163.com
 */


val Param: ViewGroup.LayoutParams
    get() {
        return ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
val MParam: ViewGroup.MarginLayoutParams
    get() {
        return ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
    }


val <T : ViewGroup.LayoutParams> T.Wrap: T
    get() {
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        this.width = ViewGroup.LayoutParams.WRAP_CONTENT
        return this
    }

val <T : ViewGroup.LayoutParams> T.WrapW: T
    get() {
        this.width = ViewGroup.LayoutParams.WRAP_CONTENT
        return this
    }


val <T : ViewGroup.LayoutParams> T.WrapH: T
    get() {
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        return this
    }

val <T : ViewGroup.LayoutParams> T.Fill: T
    get() {
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = ViewGroup.LayoutParams.MATCH_PARENT
        return this
    }

val <T : ViewGroup.LayoutParams> T.FillW: T
    get() {
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        return this
    }

val <T : ViewGroup.LayoutParams> T.FillH: T
    get() {
        this.height = ViewGroup.LayoutParams.MATCH_PARENT
        return this
    }

fun <T : ViewGroup.LayoutParams> T.size(w: Int, h: Int = w): T {
    return width(w).height(h)
}


fun <T : ViewGroup.LayoutParams> T.width(w: Int): T {
    if (w > 0) {
        this.width = w.dp
    } else {
        this.width = w
    }
    return this
}

fun <T : ViewGroup.LayoutParams> T.widthPx(w: Int): T {
    this.width = w
    return this
}


fun <T : ViewGroup.LayoutParams> T.height(h: Int): T {
    if (h > 0) {
        this.height = h.dp
    } else {
        this.height = 0
    }
    return this
}

fun <T : ViewGroup.LayoutParams> T.heightPx(h: Int): T {
    this.height = h
    return this
}

val <T : ViewGroup.LayoutParams> T.HeightButton: T
    get() {
        return height(ViewSize.ButtonHeight)
    }

val <T : ViewGroup.LayoutParams> T.HeightBar: T
    get() {
        return height(ViewSize.BarHeight)
    }


val <T : ViewGroup.LayoutParams> T.HeightButtonSmall: T
    get() {
        return height(ViewSize.ButtonHeightSmall)
    }


val <T : ViewGroup.LayoutParams> T.HeightEdit: T
    get() {
        return height(ViewSize.EditHeight)
    }


val <T : ViewGroup.LayoutParams> T.HeightEditSmall: T
    get() {
        return height(ViewSize.EditHeightSmall)
    }


val <T : ViewGroup.LayoutParams> T.HeightEditSearch: T
    get() {
        return height(ViewSize.EditHeightSearch)
    }


fun <T : ViewGroup.MarginLayoutParams> T.marginLeft(v: Int): T {
    this.leftMargin = v.dp
    return this
}

fun <T : ViewGroup.MarginLayoutParams> T.marginRight(v: Int): T {
    this.rightMargin = v.dp
    return this
}

fun <T : ViewGroup.MarginLayoutParams> T.marginTop(v: Int): T {
    this.topMargin = v.dp
    return this
}

fun <T : ViewGroup.MarginLayoutParams> T.marginBottom(v: Int): T {
    this.bottomMargin = v.dp
    return this
}

fun <T : ViewGroup.MarginLayoutParams> T.margins(m: Int): T {
    val v = m.dp
    this.setMargins(v, v, v, v)
    return this
}

fun <T : ViewGroup.MarginLayoutParams> T.margins(left: Int, top: Int, right: Int, bottom: Int): T {
    this.setMargins(left.dp, top.dp, right.dp, bottom.dp)
    return this
}

fun <T : ViewGroup.MarginLayoutParams> T.marginsPx(left: Int, top: Int, right: Int, bottom: Int): T {
    this.setMargins(left, top, right, bottom)
    return this
}

fun <T : ViewGroup.MarginLayoutParams> T.margins(hor: Int, ver: Int): T {
    return margins(hor, ver, hor, ver)
}

fun <T : ViewGroup.MarginLayoutParams> T.marginX(left: Int, right: Int): T {
    this.setMargins(left.dp, this.topMargin, right.dp, this.bottomMargin)
    return this
}

fun <T : ViewGroup.MarginLayoutParams> T.marginX(x: Int): T {
    this.setMargins(x.dp, this.topMargin, x.dp, this.bottomMargin)
    return this
}

fun <T : ViewGroup.MarginLayoutParams> T.marginY(top: Int, bottom: Int): T {
    this.setMargins(this.leftMargin, top.dp, this.rightMargin, bottom.dp)
    return this
}

fun <T : ViewGroup.MarginLayoutParams> T.marginY(y: Int): T {
    this.setMargins(this.leftMargin, y.dp, this.rightMargin, y.dp)
    return this
}






