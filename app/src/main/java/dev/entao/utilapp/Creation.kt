package dev.entao.utilapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import dev.entao.viewbuilder.LinearParams


fun ViewGroup.Button(lp: ViewGroup.LayoutParams, block: Button.() -> Unit) {
    val b = Button(this.context)
    this.addView(b, lp)
    b.block()
}

fun ViewGroup.Button(block: Button.() -> Unit) {
    val b = Button(this.context)
//    this.addView(b, lp)
    b.block()
}

fun View.linearParams(block: LinearParams.() -> Unit) {
    val p = LinearParams(LinearParams.WRAP_CONTENT, LinearParams.WRAP_CONTENT)
    this.layoutParams = p
    p.block()
}


fun hello() {
    var g: LinearLayout? = null
    g?.Button(LinearLayout.LayoutParams(10, 20)) {

    }
}