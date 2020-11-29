@file:Suppress("unused")

package dev.entao.kan.base

import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.IllegalArgumentException
import java.security.MessageDigest
import java.text.Collator
import java.util.*

const val UTF8 = "UTF-8"
private const val PROGRESS_DELAY = 50

typealias BlockUnit = () -> Unit

fun errorArg(message: Any): Nothing = throw IllegalArgumentException(message.toString())


fun <T : Closeable> T?.closeSafe() {
    try {
        this?.close()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

@Throws(IOException::class)
fun copyStream(
    input: InputStream,
    closeIs: Boolean,
    os: OutputStream,
    closeOs: Boolean,
    total: Int,
    progress: Progress?
) {
    try {
        progress?.onProgressStart(total)

        val buf = ByteArray(4096)
        var pre = System.currentTimeMillis()
        var recv = 0

        var n = input.read(buf)
        while (n != -1) {
            os.write(buf, 0, n)
            recv += n
            if (progress != null) {
                val curr = System.currentTimeMillis()
                if (curr - pre > PROGRESS_DELAY) {
                    pre = curr
                    progress.onProgress(recv, total, if (total > 0) recv * 100 / total else 0)
                }
            }
            n = input.read(buf)
        }
        os.flush()
        progress?.onProgress(recv, total, if (total > 0) recv * 100 / total else 0)
    } finally {
        if (closeIs) {
            input.closeSafe()
        }
        if (closeOs) {
            os.closeSafe()
        }
        progress?.onProgressFinish()
    }
}


val collatorChina = Collator.getInstance(Locale.CHINA)
val chinaComparator = Comparator<String> { left, right -> collatorChina.compare(left, right) }

class ChinaComparator<T>(val block: (T) -> String) : Comparator<T> {
    override fun compare(o1: T, o2: T): Int {
        return chinaComparator.compare(block(o1), block(o2))
    }
}


inline fun <T : Closeable> T?.useSafe(block: (T) -> Unit) {
    try {
        if (this != null) {
            block(this)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    } finally {
        this.closeSafe()
    }
}

inline fun <T : Closeable> T.closeAfter(block: (T) -> Unit): Unit {
    try {
        block(this)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            this.close()
        } catch (ex: Exception) {
        }
    }
}

fun md5(value: String): String? {
    try {
        val md5 = MessageDigest.getInstance("MD5")
        md5.update(value.toByteArray())
        val m = md5.digest()
        return Hex.encode(m)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 * @param max
 * @return [0-max]
 */
fun random(max: Int): Int {
    return Random(System.nanoTime()).nextInt(max + 1)
}

/**
 * @param min
 * @param max
 * @return [min, max]
 */
fun random(min: Int, max: Int): Int {
    val max2 = max - min
    val ret = random(max2)
    return ret + min
}


fun Sleep(millSeconds: Long) {
    try {
        Thread.sleep(millSeconds)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

}

fun Sleep(ms: Int) {
    Sleep(ms.toLong())
}


object Rand {
    val random = Random(System.nanoTime())

    //[0, bound)
    fun int(bound: Int): Int {
        return random.nextInt(bound)
    }

    //[from, toValue)
    fun int(from: Int, toValue: Int): Int {
        val dist = toValue - from
        if (dist > 0) {
            return random.nextInt(dist) + dist
        }
        error("Assertion failed")
    }

    //[1000, 9999]
    fun code4(): String {
        return this.int(1000, 10000).toString()
    }

    //[100000,999999]
    fun code6(): String {
        return this.int(100000, 1000000).toString()
    }
}