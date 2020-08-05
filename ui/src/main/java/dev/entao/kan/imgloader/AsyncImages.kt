@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.entao.kan.imgloader

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.LruCache
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import dev.entao.kan.appbase.*
import dev.entao.kan.ext.leftImage
import dev.entao.kan.ext.requireId
import dev.entao.kan.ext.rightImage
import dev.entao.kan.ext.topImage
import dev.entao.kan.util.uriLocal
import java.lang.ref.WeakReference

fun ImageView.loadURL(url: String, maxEdge: Int) {
    HttpImage(url).opt { limit(maxEdge) }.display(this)
}

fun ImageView.loadURL(url: String, block: (ImageOption) -> Unit) {
    val h = HttpImage(url)
    block(h.option)
    h.display(this)
}

fun ImageView.loadRes(resId: Int, block: (ImageOption) -> Unit) {
    val h = ResImage(resId)
    block(h.option)
    h.display(this)
}

fun ImageView.loadUri(uri: Uri, block: (ImageOption) -> Unit) {
    val h = UriImage(uri)
    block(h.option)
    h.display(this)
}


object ImageCache {
    private val cache = object : LruCache<String, Bitmap>(6 * 1024 * 1024) {
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.rowBytes * value.height
        }
    }

    fun find(key: String): Bitmap? {
        return cache.get(key)
    }

    fun put(key: String, bmp: Bitmap) {
        cache.put(key, bmp)
    }

    fun remove(key: String): Bitmap? {
        return cache.remove(key)
    }
}

fun <T : AsyncImage> T.opt(block: ImageOption.() -> Unit): T {
    option.block()
    return this
}

@SuppressLint("UseSparseArrays")


abstract class AsyncImage(val imageIdent: String) {

    val option = ImageOption()

    abstract fun bitmap(block: (Bitmap?) -> Unit)
    abstract fun existLocalCache(): Boolean


    fun keyString(): String {
        return "${option.keyString}@$imageIdent"
    }


    fun display(view: View, block: (View, Drawable?) -> Unit) {
        viewMap[view.requireId()] = imageIdent
        val a = ImageCache.find(keyString())
        if (a != null) {
            makeViewImage(a, view, block)
            return
        }
        if (existLocalCache()) {
            bitmap {
                makeViewImage(it, view, block)
            }
            return
        }
        if (option.defaultImage != 0) {
            block(view, makeDrawable(option.defaultImage.resBitmap.limit(option.limit)))
        }
        val weakView = WeakReference<View>(view)
        bitmap { bmp ->
            Task.fore {
                val v = weakView.get()
                if (v != null) {
                    makeViewImage(bmp, v, block)
                }
            }
        }
    }

    private fun makeDrawable(bmp: Bitmap): Drawable? {
        return if (option.circled) {
            bmp.circled
        } else if (option.corners > 0) {
            bmp.rounded(option.corners)
        } else {
            bmp.drawable
        }
    }

    private fun makeViewImage(bmp: Bitmap?, view: View, block: (View, Drawable?) -> Unit) {
        if (viewMap[view.requireId()] != this.imageIdent) {
            return
        }
        if (bmp != null) {
            ImageCache.put(keyString(), bmp)
            block(view, makeDrawable(bmp))
        } else if (option.failedImage != 0) {
            block(view, makeDrawable(option.failedImage.resBitmap.limit(option.limit)))
        } else {
            block(view, null)
        }
    }


    fun display(imageView: ImageView) {
        display(imageView) { v, d ->
            v as ImageView
            v.setImageDrawable(d)
        }
    }

    fun rightImage(textView: TextView) {
        display(textView) { v, d ->
            v as TextView
            v.rightImage(d)
        }
    }

    fun leftImage(textView: TextView) {
        display(textView) { v, d ->
            v as TextView
            v.leftImage(d)
        }
    }

    fun topImage(textView: TextView) {
        display(textView) { v, d ->
            v as TextView
            v.topImage(d)
        }
    }

    companion object {
        private val viewMap: HashMap<Int, String> = HashMap()
    }

}

class UriImage(val uri: Uri) : AsyncImage(uri.toString()) {

    override fun bitmap(block: (Bitmap?) -> Unit) {
        val b = Bmp.uri(uri, option.limit, option.quility)
        block(b)
    }

    override fun existLocalCache(): Boolean {
        return true
    }
}

class ResImage(val resId: Int) : AsyncImage("resDrawable@$resId") {

    override fun bitmap(block: (Bitmap?) -> Unit) {
        if (resId == 0) {
            block(null)
        } else {
            val bmp = resId.resBitmap
            val b = bmp.limit(option.limit)
            block(b)
        }
    }

    override fun existLocalCache(): Boolean {
        return true
    }
}


class HttpImage(val url: String) : AsyncImage(url) {


    override fun bitmap(block: (Bitmap?) -> Unit) {
        if (option.forceDownload) {
            FileDownloader.download(url) {
                val b = Bmp.uri(it?.uriLocal, option.limit, option.quility)
                block(b)
            }
        } else {
            FileDownloader.retrive(url) {
                val b = Bmp.uri(it?.uriLocal, option.limit, option.quility)
                block(b)
            }
        }

    }

    override fun existLocalCache(): Boolean {
        return FileLocalCache.find(url) != null
    }

    companion object {
        fun batch(ls: List<String>, optBlock: ImageOption.() -> Unit, callback: (List<Bitmap>) -> Unit) {
            val bls = ArrayList<Bitmap?>()
            if (ls.isEmpty()) {
                callback(emptyList())
                return
            }
            ls.forEach { url ->
                HttpImage(url).opt(optBlock).bitmap { bmp ->
                    bls += bmp
                    if (bls.size == ls.size) {
                        callback(bls.filterNotNull())
                    }
                }
            }
        }
    }
}