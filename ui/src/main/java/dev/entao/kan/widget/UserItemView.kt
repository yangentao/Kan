package dev.entao.kan.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import dev.entao.kan.creator.imageView
import dev.entao.kan.creator.textView
import dev.entao.kan.ext.*
import dev.entao.kan.base.ImageDef

class UserItemView(context: Context) : RelativeLayout(context) {

	val portraitView: ImageView
	val nameView: TextView
	val statusView: TextView

	init {
		portraitView = imageView(RParam.Left.CenterY.size(64).margins(15)) {
			scaleCenterCrop()
			setImageResource(ImageDef.portrait)
		}

		nameView = textView(RParam.toRight(portraitView).Top.Wrap.margins(0, 20, 0, 10)) {
			textSizeA()
			textColorMajor()
		}
		statusView = textView(RParam.toRight(portraitView).below(nameView).Wrap) {
			textSizeB()
			textColorMinor()
		}

		imageView(RParam.Right.CenterY.size(14).marginRight(10)) {
			setImageResource(ImageDef.more)
		}
	}

	fun bindValues(name: String, status: String) {
		this.nameView.text = name
		this.statusView.text = status
	}

	fun portrait(resId: Int) {
		portraitView.setImageResource(resId)
	}

	fun portrait(d: Drawable) {
		portraitView.setImageDrawable(d)
	}
}