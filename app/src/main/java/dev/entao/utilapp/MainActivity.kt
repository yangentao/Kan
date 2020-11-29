package dev.entao.utilapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toolbar
import dev.entao.kan.base.StackActivity
import dev.entao.kan.ext.Bottom
import dev.entao.kan.ext.LParam
import dev.entao.kan.ext.Wrap
import dev.entao.kan.ext.WrapH
import dev.entao.kan.qrx.QRPageX
import dev.entao.viewbuilder.*

//import dev.entao.kan.log.logd

class MainActivity : StackActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentPage(QRPageX())

        val l = android.widget.LinearLayout(this)

        ContentView = LinearLayout {
            Button {
                linearParams {
                    WrapH.Bottom
                }
                onClick(::clickButton)
                onClickView(::clickButton2)
            }
            Append<Toolbar> {

            }
            val a = View(this.context)
        }


    }

    fun clickButton() {

    }

    fun clickButton2(b: Button) {

    }

}
