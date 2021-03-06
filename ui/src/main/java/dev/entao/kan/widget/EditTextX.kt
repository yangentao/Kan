package dev.entao.kan.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import dev.entao.kan.appbase.Task
import dev.entao.kan.appbase.dp
import dev.entao.kan.appbase.sized
import dev.entao.kan.appbase.resDrawable
import dev.entao.kan.ext.hideInputMethod
import dev.entao.kan.ext.needId
import dev.entao.kan.ext.padding
import dev.entao.kan.ui.R

class EditTextX(context: Context) : EditText(context) {

    init {
        needId()
        padding(5, 2, 5, 2)
        this.setOnTouchListener(View.OnTouchListener { _, event ->
            if (this@EditTextX.compoundDrawables[2] == null) {
                return@OnTouchListener false
            }
            if (event.action != MotionEvent.ACTION_UP) {
                return@OnTouchListener false
            }
            if (event.x > this@EditTextX.width - this@EditTextX.paddingRight
                - IMAGE_WIDTH - 15.dp
            ) {
                this@EditTextX.setText("")
                this@EditTextX.setCompoundDrawables(null, null, null, null)
                Task.fore {
                    this@EditTextX.hideInputMethod()
                }
            }
            false
        })
        this.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                this@EditTextX.setCompoundDrawables(
                    null, null, if (this@EditTextX.text.toString() == "")
                        null
                    else
                        R.drawable.yet_edit_clear.resDrawable.sized(EditTextX.IMAGE_WIDTH), null
                )
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    companion object {
        val IMAGE_WIDTH = 25
    }
}
