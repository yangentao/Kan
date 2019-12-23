@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package dev.entao.kan.base

import android.graphics.Bitmap
import android.net.Uri
import dev.entao.kan.appbase.App
import dev.entao.kan.appbase.ex.Bmp
import dev.entao.kan.appbase.ex.saveJpg
import dev.entao.kan.appbase.ex.savePng
import dev.entao.kan.appbase.ex.scale
import dev.entao.kan.util.uriLocal
import java.io.File
import kotlin.math.max


class ImageCast(val uri: Uri) {
    var limitEdge: Int = 0
    var equalEdge: Boolean = false

    fun limit(edge: Int): ImageCast {
        this.limitEdge = edge
        this.equalEdge = false
        return this
    }

    fun edge(edge: Int): ImageCast {
        this.limitEdge = edge
        this.equalEdge = true
        return this
    }

    fun portrait(): ImageCast {
        return limit(256)
    }

    private fun save(png: Boolean): File? {
        val bmp = this.bmp ?: return null
        val f = App.files.ex.tempFile(if (png) ("png") else "jpg")
        if (png) {
            bmp.savePng(f)
        } else {
            bmp.saveJpg(f)
        }
        if (f.exists()) {
            return f
        }
        return null
    }

    val bmp: Bitmap?
        get() {
            var bmp = Bmp.uri(uri, limitEdge, Bitmap.Config.ARGB_8888) ?: return null
            val bmpMaxEdge = max(bmp.width, bmp.height)
            if (equalEdge && limitEdge > 0 && bmpMaxEdge > 0 && bmpMaxEdge != limitEdge) {
                val scale: Float = limitEdge * 1.0f / bmpMaxEdge
                bmp = bmp.scale(scale)
            }
            return bmp
        }

    val jpgFile: File? get() = save(false)
    val pngFile: File? get() = save(true)
    val jpgUri: Uri? get() = this.jpgFile?.uriLocal
    val pngUri: Uri? get() = this.pngFile?.uriLocal

}