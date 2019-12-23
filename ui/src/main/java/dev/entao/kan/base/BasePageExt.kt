package dev.entao.kan.base

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import dev.entao.kan.appbase.App
import dev.entao.kan.appbase.ex.Bmp
import dev.entao.kan.appbase.ex.saveJpg
import dev.entao.kan.appbase.ex.savePng
import dev.entao.kan.base.ex.lowerCased
import dev.entao.kan.dialogs.dialogX
import dev.entao.kan.util.UriFromSdFile
import java.io.File


fun BasePage.selectImage(width: Int, block: (Uri) -> Unit) {
    this.dialogX.showListItem(listOf("拍照", "相册"), null) {
        if (it == "拍照") {
            takePhotoJpg(width) { fff ->
                block(Uri.fromFile(fff))
            }
        } else {
            pickPhoto(width) { uu ->
                block(uu)
            }
        }
    }
}

fun BasePage.pickJpg(width: Int, block: (File) -> Unit) {
    val i = Intent(Intent.ACTION_PICK)
    i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
    val onResult = object : ActivityResultListener {
        override fun onResultOK(intent: Intent?) {
            if (intent?.data != null) {
                val outputFile = App.files.ex.temp("" + System.currentTimeMillis() + ".jpg")
                val bmp = Bmp.uri(intent.data, width, Bitmap.Config.ARGB_8888)
                if (bmp != null) {
                    bmp.saveJpg(outputFile)
                    block.invoke(outputFile)
                }
            }
        }
    }
    startActivityForResult(i, onResult)
}

fun BasePage.pickPhoto(width: Int, block: (Uri) -> Unit) {
    val i = Intent(Intent.ACTION_PICK)
    i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
    val onResult = object : ActivityResultListener {
        override fun onResultOK(intent: Intent?) {
            if (intent?.data != null) {
                val f = App.files.ex.tempFile("PNG")
                val bmp = Bmp.uri(intent.data, width, Bitmap.Config.ARGB_8888)
                if (bmp != null) {
                    bmp.savePng(f)
                    if (f.exists()) {
                        block.invoke(Uri.fromFile(f))
                    }
                }

            }
        }
    }
    startActivityForResult(i, onResult)
}

fun BasePage.takePhotoPng(width: Int, block: (File) -> Unit) {
    takePhoto(width, true, block)
}

fun BasePage.takePhotoJpg(width: Int, block: (File) -> Unit) {
    takePhoto(width, false, block)
}


fun BasePage.takePhoto(width: Int, png: Boolean, block: (File) -> Unit) {
    val fmt = if (png) "PNG" else "JPEG"
    val outputFile = App.files.ex.temp("" + System.currentTimeMillis() + "." + fmt)
    val intent = Intent("android.media.action.IMAGE_CAPTURE")
    intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0)
    val outUri = UriFromSdFile(outputFile)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri)
    intent.putExtra("outputFormat", fmt)
    val onResult = object : ActivityResultListener {
        override fun onResultOK(intent: Intent?) {
            if (outputFile.exists()) {
                val f = App.files.ex.tempFile(fmt.lowerCased)
                val bmp = Bmp.file(outputFile, width, Bitmap.Config.ARGB_8888)
                if (bmp != null) {
                    if (png) {
                        bmp.savePng(f)
                    } else {
                        bmp.saveJpg(f)
                    }
                    if (f.exists()) {
                        block(f)
                    }
                }
            }
        }

    }
    startActivityForResult(intent, onResult)
}

fun BasePage.cropPhoto(uri: Uri, outX: Int, outY: Int, result: (Bitmap?) -> Unit) {
    val intent = Intent("com.android.camera.action.CROP")
    intent.setDataAndType(uri, "image/*")
    intent.putExtra("crop", "true")
    // aspectX aspectY 是宽高的比例
    intent.putExtra("aspectX", 1)
    intent.putExtra("aspectY", 1)
    // outputX outputY 是裁剪图片宽高
    intent.putExtra("outputX", outX)
    intent.putExtra("outputY", outY)
    intent.putExtra("return-data", true)
    // intent.putExtra("output",CAMERA_EXTRA_OUTPUT_FILE);
    val onResult = object : ActivityResultListener {
        override fun onResultOK(intent: Intent?) {
            val extras = intent?.extras
            var photo: Bitmap? = null
            if (extras != null) {
                photo = extras.getParcelable("data")
            }
            result.invoke(photo)
        }

        override fun onResultFailed(intent: Intent?) {
            result.invoke(null)
        }

    }
    startActivityForResult(intent, onResult)
}

fun BasePage.takeViedo(sizeM: Int, block: (Uri) -> Unit) {
    val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
    intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, sizeM * 1024 * 1024)
    val onResult = object : ActivityResultListener {
        override fun onResultOK(intent: Intent?) {
            if (intent?.data != null) {
                block.invoke(intent.data!!)
            }
        }
    }
    startActivityForResult(intent, onResult)
}

fun BasePage.pickVideo(block: (Uri) -> Unit) {
    val i = Intent(Intent.ACTION_PICK)
    i.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*")
    val onResult = object : ActivityResultListener {
        override fun onResultOK(intent: Intent?) {
            if (intent?.data != null) {
                block.invoke(intent.data!!)
            }
        }
    }
    startActivityForResult(i, onResult)
}