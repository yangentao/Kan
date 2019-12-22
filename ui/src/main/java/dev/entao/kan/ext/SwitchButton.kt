@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.entao.kan.ext

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.view.View.OnLayoutChangeListener
import android.widget.CheckBox
import dev.entao.kan.appbase.ex.*
import dev.entao.kan.base.ColorX

/**
 * Created by entaoyang@163.com on 16/6/4.
 */


//width:60, height:30
class SwitchButton(context: Context) : CheckBox(context) {

    init {
        this.compoundDrawablePadding = 0
        this.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            this.post {
                resetImage()
            }
        }
        this.layoutParams = MParam.width(WIDTH).height(HEIGHT)
    }

    override fun setChecked(checked: Boolean) {
        val old = this.isChecked
        super.setChecked(checked)
        if (old != checked) {
            resetImage()
        }
    }

    private fun uncheckedImage(w: Int, h: Int): Drawable {
        val back = ShapeRect(ColorX.backDisabled, h / 2).size(w, h).value
        val round = ShapeOval().fill(Color.WHITE).size(h - 2).value
        val ld = LayerDrawable(arrayOf(back, round))
        val inner = 1.dp
        ld.setLayerInset(1, inner * 2, inner, (w - h).dp - 2 * inner, inner)

        return ld
    }

    private fun checkedImage(w: Int, h: Int): Drawable {
        val back = ShapeRect(ColorX.green, h / 2).size(w, h).value
        val round = ShapeOval().fill(Color.WHITE).size(h - 2).value
        val ld = LayerDrawable(arrayOf(back, round))
        val inner = 1.dp
        ld.setLayerInset(1, (w - h).dp - inner, inner, inner * 2, inner)
        return ld
    }

    private fun switchDraw(w: Int, h: Int): Drawable {
        val a = uncheckedImage(w, h)
        val b = checkedImage(w, h)
        return StateList.drawable(a, VState.Checked to b)
    }


    private fun resetImage() {
        this.buttonDrawable = switchDraw(px2dp(this.width), px2dp(this.height))
    }

    companion object {
        const val WIDTH = 60
        const val HEIGHT = 30
    }
}


