//package dev.entao.kan.qrx
//
//import android.content.Context
//import android.hardware.display.DisplayManager
//import android.util.Size
//import androidx.camera.core.*
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.LifecycleOwner
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//
//class CameraXView(context: Context) : PreviewView(context) {
//    var resolution = Size(1280, 720)
//    var onReady: () -> Unit = {}
//    var onResult: (String) -> Unit = {}
//    var aspectRatio: Int = AspectRatio.RATIO_16_9
//
//    private var imageCapture: ImageCapture? = null
//    private var imageAnalyzer: ImageAnalysis? = null
//    private var cameraInst: Camera? = null
//
//    private var exeService: ExecutorService? = null
//    private val displayManager by lazy {
//
//        context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
//    }
//
//    private val displayListener = object : DisplayManager.DisplayListener {
//        override fun onDisplayAdded(displayId: Int) = Unit
//        override fun onDisplayRemoved(displayId: Int) = Unit
//        override fun onDisplayChanged(displayId: Int) {
//            imageCapture?.targetRotation = this@CameraXView.display.rotation
//            imageAnalyzer?.targetRotation = this@CameraXView.display.rotation
//        }
//    }
//
//    @Suppress("unused")
//    fun stop() {
//        this.imageCapture = null
//        this.imageAnalyzer = null
//        this.cameraInst = null
//        exeService?.shutdown()
//        exeService = null
////        displayManager.unregisterDisplayListener(displayListener)
//    }
//
//    fun start(lifeOwner: LifecycleOwner) {
////        displayManager.registerDisplayListener(displayListener, null)
//
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
//        cameraProviderFuture.addListener(Runnable {
//            val cameraProvider = cameraProviderFuture.get()
//            cameraProvider.unbindAll()
//            doStart(lifeOwner, cameraProvider)
//            onReady()
//        }, ContextCompat.getMainExecutor(context))
//    }
//
//    private fun doStart(lifeOwner: LifecycleOwner, cameraProvider: ProcessCameraProvider) {
//        val rotation = this.display?.rotation ?: return
//        val lens = when {
//            cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) -> {
//                CameraSelector.LENS_FACING_BACK
//            }
//            cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) -> {
//                CameraSelector.LENS_FACING_FRONT
//            }
//            else -> {
//                return
//            }
//        }
//
//        val cameraSelector = CameraSelector.Builder().requireLensFacing(lens).build()
//
//        val pre = Preview.Builder().apply {
//            setTargetAspectRatio(aspectRatio)
//            setTargetRotation(rotation)
////            setTargetResolution(resolution)
//        }.build()
//
//
//        val qrAnaly = QRImageAnalysis()
//        qrAnaly.onResult = {
//            invokeResult(it)
//        }
//        val cameraExec: ExecutorService = Executors.newSingleThreadExecutor()
//        exeService = cameraExec
//        val imgAnalyzer = ImageAnalysis.Builder()
//            .setTargetAspectRatio(aspectRatio)
//            .setTargetRotation(rotation)
//            .build().also {
//                it.setAnalyzer(cameraExec, qrAnaly)
////                it.setAnalyzer(cameraExec, HelloAnalyzer())
//            }
//
//        val imageCapture =
//            ImageCapture.Builder()
//                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//                .setTargetAspectRatio(aspectRatio)
//                .setTargetRotation(rotation)
//                .build()
//        val camera = cameraProvider.bindToLifecycle(
//            lifeOwner,
//            cameraSelector,
//            pre,
//            imageCapture,
//            imgAnalyzer
//
//        )
//        pre.setSurfaceProvider(this.createSurfaceProvider())
//        this.imageCapture = imageCapture
//        this.imageAnalyzer = imgAnalyzer
//        this.cameraInst = camera
//
//    }
//
//    fun isTorchOn(): Boolean {
//        return this.cameraInst?.cameraInfo?.torchState?.value == TorchState.ON
//    }
//
//    fun setTorchOn(on: Boolean) {
//        this.cameraInst?.cameraControl?.enableTorch(on)
//    }
//
//    private fun invokeResult(s: String) {
//        this.onResult(s)
//    }
//
//
//}