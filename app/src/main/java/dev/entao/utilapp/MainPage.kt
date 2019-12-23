package dev.entao.utilapp

import android.content.Context
import android.widget.LinearLayout
import dev.entao.kan.base.ImageCast
import dev.entao.kan.base.pickImage
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
        this.pickImage {
             ImageCast(it).edge(256).jpgFile
        }
    }
}