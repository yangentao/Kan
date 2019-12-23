package dev.entao.kan.base

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import dev.entao.kan.appbase.App
import dev.entao.kan.appbase.Task
import dev.entao.kan.dialogs.dialogX
import dev.entao.kan.log.logd
import dev.entao.kan.util.UriFromSdFile


fun BasePage.backLoading(block: BlockUnit) {
    showLoading()
    Task.back {
        block()
        Task.fore {
            hideLoading()
        }
    }
}

fun BasePage.selectPortrait(block: (Bitmap) -> Unit) {
    selectImage { uri ->
        cropImage(uri, 256, 256) {
            if (it != null) {
                block(it)
            }
        }
    }
}

fun BasePage.selectImage(block: (Uri) -> Unit) {
    this.dialogX.showListItem(listOf("拍照", "相册"), null) { s ->
        if (s == "拍照") {
            takeImage {
                block(it)
            }
        } else {
            pickImage {
                block(it)
            }
        }
    }
}


fun BasePage.pickImage(block: (Uri) -> Unit) {
    val i = Intent(Intent.ACTION_PICK)
    i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")

    startActivityResultUri(i) { data ->
        block(data)
    }
}


fun BasePage.takeImage(block: (Uri) -> Unit) {
    val fmt = "JPEG"
    val outputFile = App.files.ex.tempFile(fmt)
    val outUri = UriFromSdFile(outputFile)
    val intent = Intent("android.media.action.IMAGE_CAPTURE")
    intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri)
    intent.putExtra("outputFormat", fmt)
    startActivityResultOK(intent) {
        logd("Intent? ", it)
        logd("Data? ", it?.data)
        if (outputFile.exists()) {
            block(outUri)
        }
    }
}

fun BasePage.cropImage(uri: Uri, outX: Int, outY: Int, result: (Bitmap?) -> Unit) {
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
    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
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
    startActivityResultUri(intent) {
        block.invoke(it)
    }
}

fun BasePage.pickVideo(block: (Uri) -> Unit) {
    val i = Intent(Intent.ACTION_PICK)
    i.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*")
    startActivityResultUri(i) {
        block.invoke(it)
    }
}