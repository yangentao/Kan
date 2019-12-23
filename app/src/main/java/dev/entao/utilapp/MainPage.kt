package dev.entao.utilapp

import android.content.Context
import android.widget.LinearLayout
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
        this.selectImage(256) {
            println(it)
        }
    }
}