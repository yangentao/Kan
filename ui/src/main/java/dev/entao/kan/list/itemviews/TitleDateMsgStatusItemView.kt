@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.entao.kan.list.itemviews

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import dev.entao.kan.appbase.ex.RGB
import dev.entao.kan.appbase.ex.ShapeOval
import dev.entao.kan.base.MyDate
import dev.entao.kan.creator.createLinearHorizontal
import dev.entao.kan.creator.createLinearVertical
import dev.entao.kan.creator.createTextViewB
import dev.entao.kan.creator.createTextViewC
import dev.entao.kan.ext.*

/**
 * Created by entaoyang@163.com on 16/3/13.
 */

// 有3个文本的view, name和date在一行, msg在他们的下面
// name----------------date
// msg-----------------status
class TitleDateMsgStatusItemView(context: Context) : HorItemView(context) {
    private val verLayout: LinearLayout = createLinearVertical()
    val titleView: TextView
    val dateView: TextView
    val msgView: TextView
    val statusView: TextView

    init {
        val top = createLinearHorizontal().gravityCenterVertical()
        titleView = createTextViewB().singleLine().ellipsizeEnd()
        dateView = createTextViewC().singleLine()

        top.addView(titleView, LParam.WidthFlex.heightWrap())
        top.addView(dateView, LParam.wrap().margins(5, 0, 0, 0))

        val bottom = createLinearHorizontal().gravityCenterVertical()
        msgView = createTextViewC().singleLine().ellipsizeEnd()
        statusView = createTextViewC().singleLine()
        bottom.addView(msgView, LParam.WidthFlex.heightWrap())
        bottom.addView(statusView, LParam.wrap().margins(5, 0, 0, 0))

        verLayout.addView(top, LParam.widthFill().heightWrap())
        verLayout.addView(bottom, LParam.widthFill().heightWrap().margins(0, 5, 0, 0))
        this.addView(verLayout, LParam.widthFill().heightWrap())
    }

    fun title(title: String): TitleDateMsgStatusItemView {
        titleView.text = title
        return this
    }

    fun msg(msg: String): TitleDateMsgStatusItemView {
        msgView.text = msg
        return this
    }

    fun date(date: String): TitleDateMsgStatusItemView {
        dateView.text = date
        return this
    }

    fun date(date: Long): TitleDateMsgStatusItemView {
        dateView.text = MyDate(date).formatShort()
        return this
    }

    fun status(status: String): TitleDateMsgStatusItemView {
        statusView.text = status
        return this
    }

    fun setValues(name: String, date: String, msg: String, status: String?): TitleDateMsgStatusItemView {
        titleView.text = name
        dateView.text = date
        msgView.text = msg
        statusView.text = status
        return this
    }

    fun setValues(name: String, date: Long, msg: String, status: String?): TitleDateMsgStatusItemView {
        val s = if (date == 0L) "" else MyDate(date).formatShort()
        return setValues(name, s, msg, status)
    }

    fun showRedPoint(show: Boolean): TitleDateMsgStatusItemView {
        statusView.setCompoundDrawables(null, null, if (show) redDrawable else null, null)
        return this
    }

    companion object {

        fun create(context: Context): TitleDateMsgStatusItemView {
            return TitleDateMsgStatusItemView(context)
        }

        private val redDrawable = ShapeOval().fill(RGB(255, 128, 0)).size(10).value
    }

}
