@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package dev.entao.kan.base

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import dev.entao.kan.appbase.App
import dev.entao.kan.appbase.ex.*
import dev.entao.kan.util.uriLocal
import java.io.File
import kotlin.math.max


val Bitmap.circled: Drawable
    get() {
        val d = RoundedBitmapDrawableFactory.create(App.resource, this)
        d.isCircular = true
        return d
    }
val Bitmap.rounded: Drawable
    get() {
        return this.rounded(8)
    }

fun Bitmap.rounded(corner: Int): Drawable {
    val d = RoundedBitmapDrawableFactory.create(App.resource, this)
    d.cornerRadius = corner.dpf
    return d
}

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