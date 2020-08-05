package dev.entao.kan.ext

import android.widget.AbsListView
import android.widget.ListView

/**
 * Created by entaoyang@163.com on 2016-08-03.
 */

val ListParam: AbsListView.LayoutParams
    get() {
        return AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT)
    }
//fun AbsListView.LayoutParams.

fun ListView.scrollToBottom() {
    val c = this.adapter?.count ?: return
    if (c > 0) {
        this.setSelection(c - 1)
    }
}