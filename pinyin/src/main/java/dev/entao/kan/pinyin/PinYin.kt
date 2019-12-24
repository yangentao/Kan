package dev.entao.kan.pinyin

import android.app.Application
import android.content.res.AssetManager

//使用前先设置PinYin.app
object PinYin {
    private val cacheMap = HashMap<Char, String>(25000)
    private var loaded: Boolean = false
    var app: Application? = null

    @Synchronized
    private fun preLoad() {
        if (loaded) {
            return
        }
        val ap = this.app ?: throw IllegalStateException("请先设置PinYin.app属性")
        try {
            val inStream = ap.assets.open("yang_pinyin.data", AssetManager.ACCESS_BUFFER)
            val br = inStream.bufferedReader()
            br.forEachLine { line ->
                if (line.length >= 3) {
                    val ch = line[0]
                    val py = line.substring(2)
                    this.cacheMap[ch] = py
                }
            }
            inStream.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        loaded = true
    }

    fun findOne(ch: Char): String? {
        if (!loaded) {
            this.preLoad()
        }
        return this.cacheMap[ch]
    }


}