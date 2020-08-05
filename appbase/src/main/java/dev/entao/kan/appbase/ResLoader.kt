@file:Suppress("unused")

package dev.entao.kan.appbase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.content.res.AppCompatResources

/**
 * Created by entaoyang@163.com on 2016-10-16.
 */

val Int.colorDrawable: ColorDrawable get() = ColorDrawable(this)

@Suppress("DEPRECATION")
val Int.resColor: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            App.resource.getColor(this, App.inst.theme)
        } else {
            App.resource.getColor(this)
        }
    }
val Int.resDrawable: Drawable
    get() {
        return AppCompatResources.getDrawable(App.inst, this)!!
    }
val Int.resBitmap: Bitmap
    get() {
        return BitmapFactory.decodeResource(App.resource, this)
    }


val Int.resString: String
    get() {
        return App.resource.getString(this)
    }

fun Int.resStrArgs(vararg args: Any): String {
    return App.resource.getString(this, *args)
}




