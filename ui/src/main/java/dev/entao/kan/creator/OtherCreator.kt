package dev.entao.kan.creator

import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.entao.kan.ext.needId

/**
 * Created by entaoyang@163.com on 2018-04-18.
 */

fun ViewGroup.swipeRefresh(block: SwipeRefreshLayout.() -> Unit): SwipeRefreshLayout {
	val v = SwipeRefreshLayout(context).needId()
	addView(v)
	v.block()
	return v
}

fun ViewGroup.swipeRefresh(param: ViewGroup.LayoutParams, block: SwipeRefreshLayout.() -> Unit): SwipeRefreshLayout {
	val v = SwipeRefreshLayout(context).needId()
	addView(v, param)
	v.block()
	return v
}