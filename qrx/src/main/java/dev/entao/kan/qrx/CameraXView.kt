package dev.entao.kan.qrx

import android.content.Context
import android.graphics.Matrix
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.TextureView
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import dev.entao.kan.appbase.TaskQueue

class CameraXView(context: Context) : PreviewView(context) {
    private val taskHandler: TaskQueue = TaskQueue("image_analysis")
    var preview: Preview? = null
    var resolution = Size(1280, 720)
    var onReady: () -> Unit = {}
    var onResult: (String) -> Unit = {}

    fun start(lifeOwner: LifecycleOwner) {
        val ratio = Rational(9, 16)
        val rotation = this.display.rotation


        val cpf = ProcessCameraProvider.getInstance(context)

        cpf.addListener(Runnable {
            val cp = cpf.get()
            cp.unbindAll()
            val pre = Preview.Builder().apply {
                setTargetAspectRatio(AspectRatio.RATIO_16_9)
                setTargetRotation(rotation)
                setTargetResolution(resolution)
            }.build()
            val ex = ContextCompat.getMainExecutor(context)

            val cs = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            val qrAnaly = QRImageAnalysis()
            qrAnaly.onResult = {
                invokeResult(it)
            }
            val imgAnalyzer = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(ex, qrAnaly)
            }

            val imgCap = ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()
            val camera = cp.bindToLifecycle(lifeOwner, cs, pre, imgCap, imgAnalyzer)
            pre.setSurfaceProvider(this.createSurfaceProvider())
//            pre.setSurfaceProvider(this.createSurfaceProvider(camera.cameraInfo))

            onReady()
        }, ContextCompat.getMainExecutor(context))


    }

    fun isTorchOn(): Boolean {
//        return this.preview?.isTorchOn ?: false
        return false
    }

    fun setTorchOn(on: Boolean) {
//        this.preview?.enableTorch(on)
    }

    private fun invokeResult(s: String) {
        this.onResult(s)
    }


}