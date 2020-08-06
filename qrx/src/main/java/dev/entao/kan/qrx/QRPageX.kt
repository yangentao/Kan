package dev.entao.kan.qrx

import android.content.Context
import android.util.Size
import android.widget.LinearLayout
import android.widget.TextView
import dev.entao.kan.appbase.Task
import dev.entao.kan.appbase.dp
import dev.entao.kan.appbase.listRes
import dev.entao.kan.appbase.sized
import dev.entao.kan.base.ManiPerm
import dev.entao.kan.base.hasPerm
import dev.entao.kan.base.popPage
import dev.entao.kan.base.reqPerm
import dev.entao.kan.creator.linearHor
import dev.entao.kan.creator.relative
import dev.entao.kan.creator.textView
import dev.entao.kan.dialogs.dialogX
import dev.entao.kan.ext.*
import dev.entao.kan.log.logd
import dev.entao.kan.page.TitlePage

class QRPageX : TitlePage() {
    private lateinit var previewView: CameraXView
    private lateinit var lightView: TextView
    var enableInputManual = false
    var onResult: (String) -> Unit = {}
    var resolution = Size(1280, 720)
    var title: String = "扫描二维码"

    fun resolution1280X720() {
        this.resolution = Size(1280, 720)
    }

    fun resolution640X480() {
        this.resolution = Size(640, 480)
    }

    override fun onCreateContent(context: Context, contentView: LinearLayout) {
        super.onCreateContent(context, contentView)
        titleBar {
            title(this@QRPageX.title)
        }
        previewView = CameraXView(context)
        previewView.resolution = this.resolution
        contentView.relative(LParam.FillW.FillH) {
            addView(previewView, RParam.FillW.FillH)
            linearHor(RParam.Wrap.Bottom.CenterX.marginBottom(20)) {
                if (enableInputManual) {
                    textView(LParam.Wrap.Center.margins(30)) {
                        text = "手动输入"
                        val a = listRes(R.mipmap.yet_qr_round) {
                            selected(R.mipmap.yet_qr_round2)
                            pressed(R.mipmap.yet_qr_round2)
                        }.sized(60)
                        compoundDrawablePadding = 2.dp
                        setCompoundDrawables(null, a, null, null)
                        gravityCenter()
                        textColorWhite()
                        this.setOnClickListener {
                            onInput()
                        }
                    }
                }
                lightView = textView(LParam.Wrap.Center.margins(30)) {
                    text = "闪光灯"
                    val a = listRes(R.mipmap.yet_qr_light) {
                        selected(R.mipmap.yet_qr_light2)
                        pressed(R.mipmap.yet_qr_light2)
                    }.sized(60)

                    compoundDrawablePadding = 2.dp
                    setCompoundDrawables(null, a, null, null)
                    gravityCenter()
                    textColorWhite()
                    this.setOnClickListener {
                        onLight()
                    }
                }

            }
        }

        previewView.onReady = {
            lightView.isSelected = previewView.isTorchOn()
        }

        previewView.onResult = {
            onQRResult(it)
        }

    }

    fun onLight() {
        val v = previewView.isTorchOn()
        previewView.setTorchOn(!v)
        lightView.isSelected = previewView.isTorchOn()
    }

    fun onInput() {
        this.dialogX.showInput("输入号码", "") {
            if (it.trim().isNotEmpty()) {
                onQRResult(it.trim())
            }
        }
    }

    private fun onQRResult(s: String) {
        logd(s)
        val a = this.onResult
        this.onResult = {}
        a(s)
        this.popPage()
    }

    override fun onResume() {
        super.onResume()
        if (hasPerm(ManiPerm.CAMERA)) {
            Task.fore {
                previewView.start(this)
            }
        } else {
            reqPerm(ManiPerm.CAMERA) {
                if (it) {
                    previewView.start(this)
                }
            }
        }
    }

    override fun onPause() {
        previewView.setTorchOn(false)
        previewView.stop()
        super.onPause()
    }

}