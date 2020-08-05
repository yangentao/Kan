package dev.entao.kan.list.itemviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.DrawableRes
import dev.entao.kan.appbase.dp
import dev.entao.kan.appbase.sized
import dev.entao.kan.ext.*
import dev.entao.kan.appbase.resDrawable
import dev.entao.kan.theme.IconSize
import dev.entao.kan.theme.Space

/**
 * Created by entaoyang@163.com on 16/3/13.
 */
class TextItemView(context: Context) : TextView(context) {
    init {
        needId()
        padding(Space.X, Space.Y, Space.X, Space.Y)
        gravityLeftCenter()
        majorStyle()
        minimumHeight = 40.dp
        this.layoutParams = MParam.FillW.WrapH
    }

    fun icon(d: Drawable?) {
        d?.sized(IconSize.Normal)
        setCompoundDrawables(d, null, null, null)
    }

    fun icon(d: Drawable?, size: Int) {
        d?.sized(size)
        setCompoundDrawables(d, null, null, null)
    }

    fun icon(@DrawableRes resId: Int, size: Int) {
        val d = resId.resDrawable.sized(size)
        setCompoundDrawables(d, null, null, null)
    }

    fun icon(@DrawableRes resId: Int) {
        val d = resId.resDrawable
        setCompoundDrawables(d, null, null, null)
    }
}
