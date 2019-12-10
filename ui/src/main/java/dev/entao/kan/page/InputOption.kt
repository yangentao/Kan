package dev.entao.kan.page

import android.view.ViewGroup
import dev.entao.kan.base.getValue
import dev.entao.kan.base.userLabel
import dev.entao.kan.base.userName
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

/**
 * Created by entaoyang@163.com on 2018-03-30.
 */
class InputOption {
	var key:String = ""
	var marginTop:Int = 10
	var marginLeft:Int = 0
	var marginRight:Int = 0
	var marginBottom:Int = 0
	var hint:String = ""
	var value:String = ""
	var height:Int = ViewGroup.LayoutParams.WRAP_CONTENT
	var inputValid = InputValid()

	var prop: KProperty<*>? = null

	fun valid(block: InputValid.()->Unit) {
		this.inputValid.block()
	}

	fun prop(p: KProperty<*>) {
		this.prop = p
		this.key = p.userName
		this.hint = p.userLabel
		if (p is KProperty0<*>) {
			value = p.getValue()?.toString() ?: ""
		}
	}

}