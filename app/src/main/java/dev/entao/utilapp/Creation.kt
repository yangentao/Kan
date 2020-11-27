package dev.entao.utilapp

import android.app.Activity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class ContentView(val activity: Activity)

val Activity.ContentView: ContentView
    get() {
        return ContentView(this)
    }
//val Fragment.ContentView: ContentView
//    get() {
//        return ContentView(this.requireActivity())
//    }

fun ContentView.LinearLayout(block: LinearLayout.() -> Unit): LinearLayout {
    val b = LinearLayout(this.activity)
    this.activity.setContentView(b)
    b.block()
    return b
}

fun ViewGroup.Button(lp: ViewGroup.LayoutParams, block: Button.() -> Unit) {
    val b = Button(this.context)
    this.addView(b, lp)
    b.block()
}

fun hello() {
    var g: LinearLayout? = null
    g?.Button(LinearLayout.LayoutParams(10, 20)) {

    }
}