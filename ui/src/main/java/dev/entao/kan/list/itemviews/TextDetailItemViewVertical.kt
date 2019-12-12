@file:Suppress("unused")

package dev.entao.kan.list.itemviews

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import dev.entao.kan.creator.createLinearVertical
import dev.entao.kan.creator.createTextView
import dev.entao.kan.ext.*

/**
 * Created by entaoyang@163.com on 16/3/13.
 */


class TextDetailItemViewVertical(context: Context) : HorItemView(context) {
    private val verLayout: LinearLayout = createLinearVertical()
    var textView: TextView = createTextView().textSizeA().textColorMajor()
    var detailView: TextView = createTextView().textSizeB().textColorMinor().singleLine()

    init {

        verLayout.addView(textView, LParam.widthFill().heightWrap().LeftCenter)
        verLayout.addView(detailView, LParam.widthFill().heightWrap().LeftCenter)
        addView(verLayout, LParam.width(0).weight(1f).heightWrap().LeftCenter)
    }

    fun setValues(text: String, detail: String?): TextDetailItemViewVertical {
        textView.text = text
        detailView.text = detail
        detailView.visibility = if (detail == null) View.GONE else View.VISIBLE
        return this
    }

    fun textSize(textSp: Int, detailSp: Int): TextDetailItemViewVertical {
        textView.textSizeSp(textSp)
        detailView.textSizeSp(detailSp)
        return this
    }

    fun textColor(textColor: Int, detailColor: Int): TextDetailItemViewVertical {
        textView.setTextColor(textColor)
        detailView.setTextColor(detailColor)
        return this
    }

    companion object {

        fun create(context: Context): TextDetailItemViewVertical {
            return TextDetailItemViewVertical(context)
        }
    }
}