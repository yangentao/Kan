@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package dev.entao.kan.imgloader

import dev.entao.kan.appbase.App
import dev.entao.kan.appbase.sql.MapTable
import dev.entao.kan.base.Sleep
import dev.entao.kan.base.ex.MultiHashMap
import dev.entao.kan.http.HttpGet
import dev.entao.kan.appbase.TaskQueue
import java.io.File
import java.util.*

/**
 * Created by entaoyang@163.com on 2016-11-02.
 */

interface DownListener {
    fun onDownload(url: String, ok: Boolean)
}

object FileDownloader {
    //下载中的文件
    private val processSet = HashSet<String>()

    private val listenMap = MultiHashMap<String, DownListener>()
    private val queue = TaskQueue("filedownloader")

    fun isDownloading(url: String): Boolean {
        return url in processSet
    }

    fun download(url: String, block: (File?) -> Unit) {
        queue.back {
            downloadSync(url, object : DownListener {
                override fun onDownload(url: String, ok: Boolean) {
                    val f = FileLocalCache.find(url)
                    block(f)
                }
            })
        }
    }

    private fun httpDown(url: String, file: File): Boolean {
        if (url.length < 8) {
            return false
        }
        val h = HttpGet(url)
        h.timeoutConnect(10_000)
        h.timeoutRead(10_000)
        var r = h.download(file, null)
        var ok = r.OK && file.exists() && file.length() > 0
        if (!ok) {
            Sleep(300)
            r = HttpGet(url).download(file, null)
            ok = r.OK && file.exists() && file.length() > 0
        }
        return ok
    }

    fun downloadSync(url: String, listener: DownListener? = null) {
        if (listener != null) {
            synchronized(listenMap) {
                listenMap.put(url, listener)
            }
        }
        if (!processSet.add(url)) {
            return
        }
        val tmp = App.files.ex.tempFile()
        val ok = httpDown(url, tmp)
        if (ok) {
            FileLocalCache.put(url, tmp.absolutePath)
        } else {
            tmp.delete()
        }
        processSet.remove(url)
        queue.fore {
            val ls = synchronized(listenMap) {
                listenMap.remove(url)
            }
            if (ls != null) {
                for (l in ls) {
                    l.onDownload(url, ok)
                }
            }
        }
    }

    fun retrive(url: String, block: (File?) -> Unit) {
        val f = FileLocalCache.find(url)
        if (f != null) {
            block(f)
            return
        }
        download(url, block)
    }
}

object FileLocalCache {
    //url->file
    private val map = MapTable("file_downloader")

    //查找本地
    fun find(url: String): File? {
        val f = map[url] ?: return null
        val file = File(f)
        if (file.exists()) {
            return file
        }
        map.remove(url)
        return null
    }

    fun remove(url: String) {
        val f = map[url] ?: return
        File(f).delete()
        map.remove(url)
    }

    fun put(url: String, path: String) {
        map.put(url, path)
    }
}