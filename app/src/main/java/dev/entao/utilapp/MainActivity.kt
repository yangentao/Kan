package dev.entao.utilapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.entao.kan.base.StackActivity
import dev.entao.kan.base.toast
import dev.entao.kan.ext.LParam
import dev.entao.kan.ext.Wrap
import dev.entao.kan.qrx.QRPageX

//import dev.entao.kan.log.logd

class MainActivity : StackActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentPage(QRPageX())


        ContentView.LinearLayout {
            Button(LParam.Wrap) {

            }
        }


    }


}
