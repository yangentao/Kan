package dev.entao.kan.list

import android.content.Context
import android.view.View
import android.widget.Checkable
import android.widget.CheckedTextView
import dev.entao.kan.appbase.sized
import dev.entao.kan.ext.*
import dev.entao.kan.list.itemviews.HorItemView
import dev.entao.kan.log.fatalIf
import dev.entao.kan.base.DrawableDef

class CheckView(context: Context) : HorItemView(context), Checkable {
	val checkView: CheckedTextView
	lateinit var view: View

	init {
		needId()
		horizontal()
		gravityCenterVertical()
		backColorWhiteFade()
		padding(0)

		checkView = CheckedTextView(context).needId()
//		checkView.checkMarkDrawable = D.CheckBox.mutate().sized(16)
		checkView.rightImage(DrawableDef.CheckBox.mutate().sized(20), 0)
		addView(checkView, LParam.Wrap.RightCenter.margins(10, 0, 10, 0))

	}

	fun bind(view: View): CheckView {
		fatalIf(this.childCount >= 2, "已经绑定了view")
		this.view = view
		addView(view, 0, LParam.WidthFlex.WrapH)
		return this
	}

	override fun isChecked(): Boolean {
		return checkView.isChecked
	}

	override fun setChecked(checked: Boolean) {
		if (checked == isChecked) {
			return
		}
		checkView.isChecked = checked
	}

	override fun toggle() {
		isChecked = !isChecked
	}

}