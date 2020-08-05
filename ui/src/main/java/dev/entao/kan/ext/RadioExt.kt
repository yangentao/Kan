@file:Suppress("unused")

package dev.entao.kan.ext

import android.graphics.drawable.Drawable
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.DrawableRes
import dev.entao.kan.appbase.*
import dev.entao.kan.creator.createRadioButton
import dev.entao.kan.ui.R

/**
 * Created by entaoyang@163.com on 2016-11-07.
 */

// IMAGE--Title------CHECK
fun <T : RadioButton> T.styleImageTextCheckRes(leftRes: Int): T {
    return this.styleImageTextCheckRes(leftRes, R.drawable.yet_checkbox, R.drawable.yet_checkbox_checked)
}

fun <T : RadioButton> T.styleImageTextCheckRes(@DrawableRes leftRes: Int, @DrawableRes rightNormal: Int, @DrawableRes rightChecked: Int): T {
    val rightImg = listRes(rightNormal) {
        checked(rightChecked)
    }.sized(15)
    this.buttonDrawable = null
    this.setCompoundDrawables(leftRes.resDrawable.sized(27), null, rightImg.sized(25), null)
    this.compoundDrawablePadding = 15.dp
    return this
}

fun <T : RadioButton> T.styleImageTextCheck(leftDraw: Drawable?): T {
    return styleImageTextCheck(leftDraw, R.drawable.yet_checkbox.resDrawable, R.drawable.yet_checkbox_checked.resDrawable)
}

fun <T : RadioButton> T.styleImageTextCheck(leftDraw: Drawable?, rightNormal: Drawable, rightChecked: Drawable): T {
    val rightImg = listDrawable(rightNormal) {
        checked(rightChecked)
    }.sized(15)
    this.buttonDrawable = null
    this.setCompoundDrawables(leftDraw?.sized(27), null, rightImg.sized(25), null)
    this.compoundDrawablePadding = 15.dp
    return this
}


fun RadioGroup.addRadioButton(title: String, block: RadioGroup.LayoutParams.() -> RadioGroup.LayoutParams): RadioButton {
    val view = this.context.createRadioButton()
    view.text = title
    val lp = RadioParam.HeightButton.FillW
    lp.block()
    this.addView(view, lp)
    return view
}


val RadioParam: RadioGroup.LayoutParams
    get() {
        return RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT)
    }
