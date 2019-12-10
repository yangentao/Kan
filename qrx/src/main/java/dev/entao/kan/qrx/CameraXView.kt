package dev.entao.kan.qrx

import android.content.Context
import android.graphics.Matrix
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.TextureView
import androidx.camera.core.*
import androidx.lifecycle.LifecycleOwner
import dev.entao.kan.appbase.ex.TaskHandler

class CameraXView(context: Context) : TextureView(context) {
    private val taskHandler: TaskHandler = TaskHandler("image_analysis")
    var preview: Preview? = null
    var resolution = Size(1280, 720)
    var onReady: () -> Unit = {}
    var onResult: (String) -> Unit = {}

    fun start(lifeOwner: LifecycleOwner) {
//        val w = this.width
//        val h = this.height
        val ratio = Rational(9, 16)
        val rotation = this.display.rotation


        val preConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(ratio)
            setTargetRotation(rotation)
            setLensFacing(CameraX.LensFacing.BACK)
            setTargetResolution(resolution)
        }.build()
        val pre = Preview(preConfig)
        preview = pre
        pre.setOnPreviewOutputUpdateListener {
            this.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        val alConfig = ImageAnalysisConfig.Builder().apply {
            setTargetAspectRatio(ratio)
            setTargetRotation(rotation)
            setLensFacing(CameraX.LensFacing.BACK)
            setTargetResolution(resolution)
            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
            this.setImageQueueDepth(2)
            setCallbackHandler(taskHandler.handler)
        }.build()
        val al = ImageAnalysis(alConfig).apply {
            val a = QRImageAnalysis()
            a.onResult = {
                invokeResult(it)
            }
            analyzer = a

        }
        CameraX.bindToLifecycle(lifeOwner, pre, al)
        onReady()
    }

    fun isTorchOn(): Boolean {
        return this.preview?.isTorchOn ?: false
    }

    fun setTorchOn(on: Boolean) {
        this.preview?.enableTorch(on)
    }

    private fun invokeResult(s: String) {
        this.onResult(s)
    }

    private fun updateTransform() {
        val ddd = this.display ?: return
        val deg = when (ddd.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        val m = Matrix()
        val cX = this.width / 2f
        val cY = this.height / 2f
        m.postRotate(-deg.toFloat(), cX, cY)
        this.setTransform(m)

    }

}