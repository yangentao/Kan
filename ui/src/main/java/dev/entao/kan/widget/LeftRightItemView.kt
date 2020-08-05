package dev.entao.kan.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import android.widget.TextView
import dev.entao.kan.base.ColorX
import dev.entao.kan.creator.createCheckBox
import dev.entao.kan.creator.createImageView
import dev.entao.kan.creator.createTextViewA
import dev.entao.kan.creator.createTextViewB
import dev.entao.kan.ext.*
import dev.entao.kan.base.DrawableDef

/**
 * 左右对齐listview itemview
 * addLeftXXX总是从0插入子View
 * addRightXXX总是从最后插入子View

 * @author yangentao@gmail.com
 */
class LeftRightItemView(context: Context, marginBottom: Int) : LinearLayout(context) {

    init {
        orientationHorizontal().gravityCenterVertical().padding(10, 5, 10, 5).backColor(Color.WHITE, ColorX.fade)
        this.layoutParams = LParam.FillW.height(ITEM_HEIGHT).margins(0, 0, 0, marginBottom)

        val v = View(getContext()).needId()
        addView(v, LParam.weight(1f).FillH)
    }

    fun findCheckBox(): CheckBox? {
        for (i in 0..childCount - 1) {
            val v = getChildAt(i)
            if (v is CheckBox) {
                return v
            }
        }
        return null
    }

    private fun addText(text: String, width: Int, right: Boolean, marginLeft: Int): TextView {
        if (right) {
            val tv = context.createTextViewB().textColorMinor().gravityRightCenter()
            tv.text = text
            tv.layoutParams = LParam.WrapH.width(width).RightCenter.margins(marginLeft, 0, 0, 0)
            this.addView(tv)
            return tv
        } else {
            val tv = context.createTextViewA()
            tv.text = text
            tv.layoutParams = LParam.WrapH.width(width).LeftCenter.margins(marginLeft, 0, 0, 0)
            this.addView(tv, 0)
            return tv
        }
    }

    fun addTextLeft(text: String, width: Int, marginLeft: Int): TextView {
        return addText(text, width, false, marginLeft)
    }

    fun addTextRight(text: String, width: Int, marginLeft: Int): TextView {
        return addText(text, width, true, marginLeft)
    }

    private fun addImage(d: Drawable, sizeDp: Int, right: Boolean, marginLeft: Int): ImageView {
        val iv = context.createImageView().backColorTransFade()
        iv.scaleType = ScaleType.CENTER_INSIDE
        iv.setImageDrawable(d)
        if (right) {
            iv.layoutParams = LParam.size(sizeDp).RightCenter.margins(marginLeft, 0, 0, 0)
            this.addView(iv)
        } else {
            iv.layoutParams = LParam.size(sizeDp).LeftCenter.margins(marginLeft, 0, 0, 0)
            this.addView(iv, 0)
        }
        return iv
    }

    fun addImageLeft(d: Drawable, sizeDp: Int, marginLeft: Int): ImageView {
        return addImage(d, sizeDp, false, marginLeft)
    }

    fun addImageRight(d: Drawable, sizeDp: Int, marginLeft: Int): ImageView {
        return addImage(d, sizeDp, true, marginLeft)
    }

    fun addCheckBoxRight(marginLeft: Int): CheckBox {
        val cb = context.createCheckBox()
        cb.buttonDrawable = DrawableDef.CheckBox
        cb.layoutParams =  LParam.size(20).RightCenter.margins(marginLeft, 0, 0, 0)
        this.addView(cb)
        return cb
    }

    companion object {
        val ITEM_HEIGHT = 45
    }

}
