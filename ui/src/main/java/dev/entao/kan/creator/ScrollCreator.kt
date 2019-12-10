@file:Suppress("unused")

package dev.entao.kan.creator

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import dev.entao.kan.base.act
import dev.entao.kan.ext.needId

/**
 * Created by entaoyang@163.com on 2018-03-14.
 */


//ScrollView
fun ViewGroup.scroll(block: ScrollView.() -> Unit): ScrollView {
	val v = this.createScroll()
	this.addView(v)
	v.block()
	return v
}

fun ViewGroup.scroll(param: ViewGroup.LayoutParams, block: ScrollView.() -> Unit): ScrollView {
	val v = this.createScroll()
	this.addView(v, param)
	v.block()
	return v
}

fun ViewGroup.scroll(index: Int, param: ViewGroup.LayoutParams, block: ScrollView.() -> Unit): ScrollView {
	val v = this.createScroll()
	this.addView(v, index, param)
	v.block()
	return v
}

fun ViewGroup.scrollBefore(ankor: View, param: ViewGroup.LayoutParams, block: ScrollView.() -> Unit): ScrollView {
	return this.scroll(this.indexOfChild(ankor), param, block)
}

fun View.createScroll(): ScrollView {
	return this.context.createScroll()
}

fun Fragment.createScroll(): ScrollView {
	return this.act.createScroll()
}

fun Context.createScroll(): ScrollView {
	return ScrollView(this).needId()
}




//ScrollView
fun ViewGroup.scrollHor(block: HorizontalScrollView.() -> Unit): HorizontalScrollView {
	val v = this.createScrollHor()
	this.addView(v)
	v.block()
	return v
}

//ScrollView
fun ViewGroup.scrollHor(param: ViewGroup.LayoutParams, block: HorizontalScrollView.() -> Unit): HorizontalScrollView {
	val v = this.createScrollHor()
	this.addView(v, param)
	v.block()
	return v
}

fun ViewGroup.scrollHor(index: Int, param: ViewGroup.LayoutParams, block: HorizontalScrollView.() -> Unit): HorizontalScrollView {
	val v = this.createScrollHor()
	this.addView(v, index, param)
	v.block()
	return v
}

fun ViewGroup.scrollHorBefore(ankor: View, param: ViewGroup.LayoutParams, block: HorizontalScrollView.() -> Unit): HorizontalScrollView {
	return this.scrollHor(this.indexOfChild(ankor), param, block)
}

fun View.createScrollHor(): HorizontalScrollView {
	return this.context.createScrollHor()
}

fun Fragment.createScrollHor(): HorizontalScrollView {
	return this.act.createScrollHor()
}

fun Context.createScrollHor(): HorizontalScrollView {
	return HorizontalScrollView(this).needId()
}


