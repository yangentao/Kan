package dev.entao.utilapp

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.LinearLayout
import dev.entao.kan.base.*
import dev.entao.kan.log.log
import dev.entao.kan.log.logd
import dev.entao.kan.page.TitlePage


class MainPage : TitlePage() {

    override fun onCreateContent(context: Context, contentView: LinearLayout) {
        super.onCreateContent(context, contentView)
        titleBar {
            title("Hello")
            "Image" right ::selImage
        }
    }

    fun selImage() {

        pickTime(13, 30) {
            logd(it.toTimeSQL)
        }
    }
}