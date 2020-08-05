@file:Suppress("FunctionName", "unused")

package dev.entao.kan.base

import dev.entao.kan.appbase.App
import java.io.File


fun TempFileExt(ext: String): File {
    return App.files.ex.tempFile(ext)
}

fun TempFileNamed(filename: String): File {
    return App.files.ex.temp(filename)
}


fun printX(vararg vs: Any?) {
    val s = vs.joinToString(" ") {
        it?.toString() ?: "null"
    }
    println(s)
}
