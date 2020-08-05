package dev.entao.kan.ext

import android.widget.EditText
import dev.entao.kan.base.DrawableDef
import dev.entao.kan.theme.StrDef

/**
 * Created by entaoyang@163.com on 2017-01-06.
 */

fun <T : EditText> T.styleSearch(): T {
	this.textSizeB().backDrawable(DrawableDef.InputSearch)
	this.padding(15, 2, 15, 2)
	this.hint(StrDef.SEARCH)
	return this
}