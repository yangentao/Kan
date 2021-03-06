@file:Suppress("unused")

package dev.entao.kan.list

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.BaseAdapter
import android.widget.ListView
import dev.entao.kan.appbase.listColorDrawable
import dev.entao.kan.base.ColorX
import dev.entao.kan.ext.backColorWhite
import dev.entao.kan.ext.needId

/**
 */
open class AnyListView(context: Context) : ListView(context) {
    var onItemClick: (item: Any) -> Unit = {}
    var onItemClick2: (View, Any, Int) -> Unit = { _, _, _ -> }

    init {
        needId()
        this.backColorWhite()
        cacheColorHint = 0
        selector = listColorDrawable(Color.TRANSPARENT) {
            lighted(ColorX.fade)
        }
        this.setOnItemClickListener { _, view, position, _ ->
            onItemClick(getItem(position))
            onItemClick2(view, getItem(position), position)
        }
    }


    fun getItem(position: Int): Any {
        return adapter.getItem(position)
    }

    fun notifyDataSetChanged() {
        (adapter as BaseAdapter).notifyDataSetChanged()
    }

}
