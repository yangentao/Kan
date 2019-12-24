@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.entao.kan.imgloader

import android.graphics.Bitmap
import dev.entao.kan.res.Res

class ImageOption {
    var quility: Bitmap.Config = Bitmap.Config.RGB_565
    var limit: Int = 720
    var corners: Int = 0
    var circled: Boolean = false

    var forceDownload: Boolean = false
    //加载失败的图片
    var failedImage: Int = Res.imageMiss
    //默认的图片, 下载前
    var defaultImage: Int = Res.imageMiss

    val keyString: String
        get() = "$quility:$limit"


    fun portrait(): ImageOption {
        limit256().onFailed(Res.portrait).onDefault(Res.portrait)
        quility8888()
        return this
    }


    fun forceDownload(): ImageOption {
        this.forceDownload = true
        return this
    }

    fun limit64(): ImageOption {
        return limit(64)
    }

    fun limit128(): ImageOption {
        return limit(128)
    }

    fun limit256(): ImageOption {
        return limit(256)
    }

    fun limit480(): ImageOption {
        return limit(480)
    }

    fun limit720(): ImageOption {
        return limit(720)
    }

    fun limit512(): ImageOption {
        return limit(512)
    }

    fun limit960(): ImageOption {
        return limit(960)
    }

    fun limit(n: Int): ImageOption {
        limit = n
        return this
    }

    fun quility8888(): ImageOption {
        quility = Bitmap.Config.ARGB_8888
        return this
    }

    fun quility565(): ImageOption {
        quility = Bitmap.Config.RGB_565
        return this
    }

    fun onFailed(id: Int): ImageOption {
        failedImage = id
        return this
    }

    fun onDefault(id: Int): ImageOption {
        defaultImage = id
        return this
    }
}