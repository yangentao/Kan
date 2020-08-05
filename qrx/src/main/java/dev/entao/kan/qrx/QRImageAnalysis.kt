package dev.entao.kan.qrx

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import dev.entao.kan.appbase.Task
import dev.entao.kan.log.logd
import dev.entao.kan.log.loge

class QRImageAnalysis : ImageAnalysis.Analyzer {
    private val reader: MultiFormatReader = MultiFormatReader().apply {
        setHints(
            mapOf<DecodeHintType, Collection<BarcodeFormat>>(
                Pair(DecodeHintType.POSSIBLE_FORMATS, arrayListOf(BarcodeFormat.QR_CODE))
            )
        )
    }

    private var processing = false
    var onResult: (String) -> Unit = {}


    private fun invokeResult(s: String) {
        val a = this.onResult
        this.onResult = {}
        a(s)
    }

    override fun analyze(image: ImageProxy) {
        if (processing) {
            return
        }
        processing = true
        try {
            logd("Analyze...",  image.width, image.height)
            if (image.format != ImageFormat.YUV_420_888 && image.format != ImageFormat.YUV_422_888) {
                loge("Expect ImageFormat.YUV_420_888 OR YUV_422_888")
                return
            }
            val buffer = image.planes[0].buffer
            val data = ByteArray(buffer.remaining())
            val w = image.width
            val h = image.height
            buffer.get(data)
            val src = PlanarYUVLuminanceSource(data, w, h, 0, 0, w, h, false)
            val bmp = BinaryBitmap(HybridBinarizer(src))
            try {
                val r = reader.decode(bmp)
                Task.fore {
                    invokeResult(r.text)
                }
            } catch (ex: NotFoundException) {
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        processing = false

    }

}