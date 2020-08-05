@file:Suppress("unused", "MemberVisibilityCanBePrivate", "PrivatePropertyName")

package dev.entao.kan.list.itemviews

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Checkable
import android.widget.CheckedTextView
import dev.entao.kan.ext.*
import dev.entao.kan.base.DrawableDef

/**
 * Created by entaoyang@163.com on 16/3/13.
 */

interface ItemViewCheckable : Checkable {

    var isCheckModel: Boolean

    fun setCheckboxMarginRight(dp: Int)
}


@SuppressLint("ViewConstructor")
class CheckItemView(context: Context, val itemView: View, listener: CheckStatusListener? = null) : HorItemView(context), ItemViewCheckable {
    var checkView: CheckedTextView
        private set
    private var listener: CheckStatusListener? = null

    private val MR = 10

    init {
        padding(0, 0, 0, 0)
        this.listener = listener
        addView(itemView, 0, LParam.WidthFlex.WrapH)

        checkView = CheckedTextView(context).needId().gone()
        checkView.checkMarkDrawable = DrawableDef.CheckBox
        addView(checkView, LParam.Wrap.RightCenter.margins(10, 0, MR, 0))

        if (this.listener == null && itemView is CheckStatusListener) {
            this.listener = itemView
        }
    }


    override fun isChecked(): Boolean {
        return checkView.isChecked
    }

    override fun setChecked(checked: Boolean) {
        if (checked == isChecked) {
            return
        }
        checkView.isChecked = checked
        listener?.onItemCheckChanged(this, checked)
    }

    override fun toggle() {
        isChecked = !isChecked
    }

    override var isCheckModel: Boolean
        get() = checkView.isVisiable()
        set(checkModel) {
            if (isCheckModel == checkModel) {
                return
            }
            if (checkModel) {
                isChecked = false
                checkView.visiable()
            } else {
                checkView.gone()
            }
            listener?.onItemCheckModelChanged(this, checkModel)
        }

    override fun setCheckboxMarginRight(dp: Int) {
        checkView.layoutParams = LParam.Wrap.RightCenter.margins(10, 0, MR + dp, 0)
        this.requestLayout()
    }

    interface CheckStatusListener {
        fun onItemCheckModelChanged(checkItemView: CheckItemView, checkModel: Boolean)

        fun onItemCheckChanged(checkItemView: CheckItemView, check: Boolean)
    }
}