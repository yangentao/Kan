@file:Suppress("unused")

package dev.entao.kan.list.itemviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.TextView
import dev.entao.kan.appbase.ex.dp
import dev.entao.kan.appbase.ex.sized
import dev.entao.kan.creator.textView
import dev.entao.kan.ext.*
import dev.entao.kan.res.D
import dev.entao.kan.res.Res
import dev.entao.kan.theme.Space

/**
 * Created by entaoyang@163.com on 16/3/13.
 */

open class TextDetailView(context: Context) : HorItemView(context) {
    val textView: TextView
    val detailView: TextView

    var argS: String = ""
    var argN: Int = 0

    init {
        padding(Space.X, 15, Space.X, 15).gravityCenterVertical()
        textView = textView(LParam.WidthWrap.HeightWrap.LeftCenter) {
            textSizeB().textColorMajor().singleLine()
        }
        addFlex()
        detailView = textView(LParam.Wrap.RightCenter) {
            textSizeC().textColorMinor().gravityRightCenter().multiLine()
            maxLines(2)
        }
    }

    var textValue: String?
        get() = this.textView.text?.toString()
        set(value) {
            this.textView.text = value ?: ""
        }

    var detailValue: String?
        get() = this.detailView.text?.toString()
        set(value) {
            this.detailView.text = value ?: ""
        }


    fun setValues(text: String?, detail: String?): TextDetailView {
        textView.text = text
        detailView.text = detail
        return this
    }


    fun setLeftImage(d: Drawable): TextDetailView {
        textView.setCompoundDrawables(d, null, null, null)
        textView.compoundDrawablePadding = dp(10)
        return this
    }


    fun setRightImage(d: Drawable): TextDetailView {
        detailView.setCompoundDrawables(null, null, d, null)
        detailView.compoundDrawablePadding = dp(10)
        return this
    }

    fun rightImageMore(): TextDetailView {
        val d = D.res(Res.more).sized(12)
        setRightImage(d)
        return this
    }
}

fun ViewGroup.textDetail(param: ViewGroup.LayoutParams, block: TextDetailView.() -> Unit): TextDetailView {
    val v = TextDetailView(this.context)
    this.addView(v, param)
    v.block()
    return v
}

fun ViewGroup.textDetailViewTrans(param: ViewGroup.LayoutParams, block: TextDetailView.() -> Unit): TextDetailView {
    val v = TextDetailView(this.context)
    this.addView(v, param)
    v.backColorTrans()
    v.textView.textColorWhite()
    v.detailView.textColorWhite()
    v.block()
    return v
}