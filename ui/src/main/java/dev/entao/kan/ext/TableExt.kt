@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.entao.kan.ext

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import dev.entao.kan.appbase.dp
import dev.entao.kan.base.ColorX
import dev.entao.kan.base.getValue
import dev.entao.kan.base.userLabel
import dev.entao.kan.creator.*
import dev.entao.kan.dialogs.DialogX
import dev.entao.kan.base.DrawableDef
import dev.entao.kan.theme.ViewSize
import kotlin.reflect.KProperty0

/**
 * Created by entaoyang@163.com on 2018-04-17.
 */

class FormView(val tableLayout: TableLayout) {
    fun header(label: String, label2: String) {
        tableLayout.tableRow(TParam.FillW.WrapH.marginBottom(5)) {
            setBackgroundColor(ColorX.theme)
            textView(RowParam.WrapW.HeightEditSmall) {
                text = label
                minimumWidth = 80.dp
                textColorWhite()
                gravityCenter()
            }
            textView(RowParam.WrapW.HeightEditSmall) {
                text = label2
                textColorWhite()
                gravityCenter()
            }
        }
    }

    fun row(label: String, block: (Context) -> View) {
        tableLayout.tableRow(TParam.FillW.WrapH.marginBottom(5)) {
            textView(RowParam.WrapW.HeightEditSmall) {
                text = label
                textColorMajor()
                gravityLeftCenter()
            }
            val v = block(context)
            val p = v.layoutParams ?: RowParam.WrapW.WrapH.margins(10, 0, 0, 0).CenterY
            v.minimumHeight = ViewSize.EditHeightSmall.dp
            addView(v, p)
        }
    }

    fun rowText(prop: KProperty0<*>): TextView {
        return rowText(prop.userLabel, prop.getValue()?.toString() ?: "")
    }

    fun rowText(label: String, text: String): TextView {
        val v = tableLayout.createTextViewB()
        v.gravityRightCenter()
        row(label) {
            v.text = text
            v
        }
        return v
    }

    fun rowButton(label: String, text: String): TextView {
        val v = tableLayout.createTextViewB()
        v.gravityCenter()
        v.backDrawable(DrawableDef.buttonWhite(2))
        v.clickable()
        row(label) {
            v.text = text
            v
        }
        return v
    }

    fun rowEdit(label: String, text: String = ""): EditText {
        val v = tableLayout.createEditX()
        v.gravityLeftCenter()
        v.backDrawable(DrawableDef.InputRect)
        row(label) {
            v.textS = text
            v
        }
        return v
    }

    fun rowSelect(label: String, text: String, items: List<String>): TextView {
        val s = if (text.isEmpty()) {
            "选择..."
        } else {
            text
        }
        return rowButton(label, s).clickView { tv ->
            DialogX(tv.context).showListItem(items, label) {
                tv.tag = it
                tv.textS = it
            }
        }
    }
}

fun ViewGroup.form(param: ViewGroup.LayoutParams, block: FormView.() -> Unit): TableLayout {
    val tb = table(param) {
        this.setColumnStretchable(1, true)
    }
    val f = FormView(tb)
    f.block()
    return tb
}

fun TableLayout.editOf(label: String): EditText? {
    for (row in this.childViews) {
        if (row is TableRow && row.childCount == 2) {
            val first = row.getChildAt(0)
            val second = row.getChildAt(1)
            if (first is TextView && second is EditText) {
                if (first.textS == label) {
                    return second
                }
            }
        }
    }
    return null
}

fun TableLayout.stretch(vararg cols: Int) {
    for (n in cols) {
        this.setColumnStretchable(n, true)
    }
}

fun TableLayout.header(block: TableRow.() -> Unit): TableRow {
    return tableRow(TParam.FillW.WrapH.marginBottom(5)) {
        setBackgroundColor(ColorX.theme)
        this.tag = "header"
        this.block()

    }
}

fun TableLayout.row(block: TableRow.() -> Unit): TableRow {
    return tableRow(TParam.FillW.WrapH.marginBottom(5)) {
        this.block()
    }
}

private val colParam: TableRow.LayoutParams
    get() {
        return RowParam.WrapW.HeightEditSmall.margins(3, 0, 3, 0)
    }

fun TableRow.label(label: String): TextView {
    val isHeaderRow = tag == "header"
    return textView(colParam) {
        text = label
        gravityLeftCenter()
        if (isHeaderRow) {
            textColorWhite()
        } else {
            textColorMajor()
        }
    }
}

fun TableRow.edit(text: String, withClear: Boolean = false): EditText {
    return if (withClear) {
        this.editX(colParam) {
            gravityLeftCenter()
            backDrawable(DrawableDef.InputRect)
            textS = text
        }
    } else {
        this.edit(colParam) {
            gravityLeftCenter()
            backDrawable(DrawableDef.InputRect)
            textS = text
        }
    }
}

fun TableRow.button(label: String): TextView {
    return textView(colParam) {
        text = label
        padding(5, 3, 5, 3)
        textColorMajor()
        textSizeB()
        gravityCenter()
        backDrawable(DrawableDef.buttonWhite(2))
        clickable()
    }
}

fun <T : View> T.span(n: Int): T {
    val p = this.layoutParams as TableRow.LayoutParams
    p.span(n)
    return this
}

fun <T : View> T.atColumn(n: Int): T {
    val p = this.layoutParams as TableRow.LayoutParams
    p.atColumn(n)
    return this
}


fun TableLayout.colViews(col: Int): List<View> {
    val ls = ArrayList<View>()
    for (r in this.childViews) {
        if (r is TableRow) {
            ls += r.getChildAt(col)
        }
    }
    return ls
}