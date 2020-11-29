package dev.entao.viewbuilder

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import dev.entao.kan.creator.newInstance

typealias GroupParams = ViewGroup.LayoutParams
typealias WindowParams = WindowManager.LayoutParams
typealias MarginParams = ViewGroup.MarginLayoutParams
typealias LinearParams = LinearLayout.LayoutParams
typealias RelativeParams = RelativeLayout.LayoutParams
typealias FrameParams = FrameLayout.LayoutParams
typealias ListParams = AbsListView.LayoutParams
typealias PagerParams = ViewPager.LayoutParams
typealias CoordinatorParams = CoordinatorLayout.LayoutParams
typealias DrawerParams = DrawerLayout.LayoutParams
typealias GridParams = GridLayout.LayoutParams


fun <T : View> T.onClick(block: () -> Unit): T {
    this.setOnClickListener {
        block()
    }
    return this
}

fun <T : View> T.onClickView(block: (T) -> Unit): T {
    this.setOnClickListener {
        block(this)
    }
    return this
}

//=====Activity====

var Activity.ContentView: View
    get() = this.findViewById<View>(android.R.id.content)
    set(value) = this.setContentView(value)


fun Activity.LinearLayout(block: LinearLayout.() -> Unit): LinearLayout {
    val b = LinearLayout(this)
    b.block()
    return b
}

fun Activity.RelativeLayout(block: RelativeLayout.() -> Unit): RelativeLayout {
    val b = RelativeLayout(this)
    b.block()
    return b
}

fun Activity.FrameLayout(block: FrameLayout.() -> Unit): FrameLayout {
    val b = FrameLayout(this)
    b.block()
    return b
}

//=======Fragment======
fun Fragment.LinearLayout(block: LinearLayout.() -> Unit): LinearLayout {
    val b = LinearLayout(this.requireActivity())
    b.block()
    return b
}

fun Fragment.RelativeLayout(block: RelativeLayout.() -> Unit): RelativeLayout {
    val b = RelativeLayout(this.requireActivity())
    b.block()
    return b
}

fun Fragment.FrameLayout(block: FrameLayout.() -> Unit): FrameLayout {
    val b = FrameLayout(this.requireActivity())
    b.block()
    return b
}
//=======ViewGroup==========

fun ViewGroup.LinearLayout(block: LinearLayout.() -> Unit): LinearLayout {
    val b = LinearLayout(this.context)
    b.block()
    return b
}

fun ViewGroup.RelativeLayout(block: RelativeLayout.() -> Unit): RelativeLayout {
    val b = RelativeLayout(this.context)
    b.block()
    return b
}

fun ViewGroup.FrameLayout(block: FrameLayout.() -> Unit): FrameLayout {
    val b = FrameLayout(this.context)
    b.block()
    return b
}

fun ViewGroup.DrawerLayout(block: DrawerLayout.() -> Unit): DrawerLayout {
    val b = DrawerLayout(this.context)
    b.block()
    return b
}

fun ViewGroup.ViewPager(block: ViewPager.() -> Unit): ViewPager {
    val b = ViewPager(this.context)
    b.block()
    return b
}

fun ViewGroup.ViewPager2(block: ViewPager2.() -> Unit): ViewPager2 {
    val b = ViewPager2(this.context)
    b.block()
    return b
}

inline fun <reified T : View> ViewGroup.Append(block: T.() -> Unit): T {
    val b = T::class.newInstance(this.context)
    b.block()
    return b
}