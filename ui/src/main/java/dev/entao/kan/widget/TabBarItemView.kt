package dev.entao.kan.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.RelativeLayout
import android.widget.TextView
import dev.entao.kan.appbase.*
import dev.entao.kan.base.ColorX
import dev.entao.kan.creator.textView
import dev.entao.kan.ext.*

/**
 * Created by entaoyang@163.com on 2018-04-18.
 */

open class TabBarItemView(context: Context) : RelativeLayout(context) {
    val textView: TextView
    val indicateView: TextView
    var autoTintDrawable = true
    var tabIndex: Int = 0

    init {
        needId()
        backColorWhite()
        textView = this.textView(RParam.Center.Wrap) {
            compoundDrawablePadding = 0
            textSizeD()
            gravityCenter()
            padding(1, 4, 1, 1)
            textColorList(ColorX.textPrimary) {
                lighted(ColorX.theme)
            }
        }
        indicateView = textView(RParam.Top.Right.margins(0, 5, 5, 0)) {
            textColor(Color.WHITE)
            textSizeSp(10)
            gravityCenter()
            backDrawable(shapeRect {
                fill(RGB(255, 80, 80))
                corner(7)
            })
            padding(2, 0, 2, 0)
            minimumWidth = 14.dp
            gone()
        }
    }

    //0: 不显示; <0 只显示红点; >0显示红点和数字
    fun setIndicate(n: Int) {
        if (n == 0) {
            indicateView.gone()
            return
        }
        if (n > 0) {
            indicateView.text = "$n"
            indicateView.visiable()
            return
        }
        indicateView.text = ""
        indicateView.visiable()
    }

    fun setText(text: String) {
        textView.textS = text
    }

    fun setIcon(res: Int) {
        if (res != 0) {
            setIcon(res.resDrawable)
        }
    }

    fun setIcon(d: Drawable) {
        val dd = if (autoTintDrawable) {
            d.tinted(ColorX.Unselected, ColorX.theme)
        } else {
            d.mutate()
        }
        textView.topImage(dd.sized(24), 0)
    }

    val text: String get() = textView.textS
}