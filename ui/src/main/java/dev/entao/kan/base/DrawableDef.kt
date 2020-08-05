package dev.entao.kan.base

import android.graphics.Color
import android.graphics.drawable.Drawable
import dev.entao.kan.appbase.ShapeRect
import dev.entao.kan.appbase.listDrawable
import dev.entao.kan.appbase.listRes
import dev.entao.kan.theme.ViewSize
import dev.entao.kan.ui.R

/**
 * Created by entaoyang@163.com on 2016-07-23.
 */

object DrawableDef {
    val CheckBox: Drawable
        get() = listRes(R.drawable.yet_checkbox) {
            checked(R.drawable.yet_checkbox_checked)
        }
    val Input: Drawable
        get() {
            val corner: Int = ViewSize.EditCorner
            val normal = ShapeRect(Color.WHITE, corner).stroke(1, ColorX.borderGray).value
            val focused = ShapeRect(Color.WHITE, corner).stroke(1, ColorX.EditFocus).value
            return listDrawable(normal) {
                focused(focused)
            }
        }
    val InputSearch: Drawable
        get() {
            val corner: Int = ViewSize.EditHeightSearch / 2
            val normal = ShapeRect(Color.WHITE, corner).stroke(1, ColorX.borderGray).value
            val focused = ShapeRect(Color.WHITE, corner).stroke(1, ColorX.EditFocus).value
            return listDrawable(normal) {
                focused(focused)
            }
        }

    val InputRect: Drawable
        get() {
            val normal = ShapeRect(Color.WHITE, 2).stroke(1, ColorX.borderGray).value
            val focused = ShapeRect(Color.WHITE, 2).stroke(1, ColorX.EditFocus).value
            return listDrawable(normal) {
                focused(focused)
            }
        }


    fun buttonWhite(corner: Int = ViewSize.ButtonCorner): Drawable {
        return buttonColor(Color.rgb(230, 230, 230), corner)
    }

    fun buttonColor(color: Int, corner: Int = ViewSize.ButtonCorner): Drawable {
        val normal = ShapeRect(color, corner).value
        val pressed = ShapeRect(ColorX.fade, corner).value
        val enableFalse = ShapeRect(ColorX.backDisabled, corner).value
        return listDrawable(normal) {
            pressed(pressed)
            disabled(enableFalse)
        }
    }


}