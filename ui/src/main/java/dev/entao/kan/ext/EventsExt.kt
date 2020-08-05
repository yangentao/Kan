@file:Suppress("unused")

package dev.entao.kan.ext

import android.view.View
import android.widget.Switch


/**
 * Created by yangentao on 2015/11/21.
 * entaoyang@163.com
 */

fun <T : View> T.clickView(block: (T) -> Unit): T {
    this.setOnClickListener {
        block(this)
    }
    return this
}

fun <T : View> T.click(block: () -> Unit): T {
    this.setOnClickListener {
        block()
    }
    return this
}


fun <T : Switch> T.onCheckChanged(block: (Switch, Boolean) -> Unit): T {
    this.setOnCheckedChangeListener { view, check ->
        block.invoke(view as Switch, check)
    }
    return this
}