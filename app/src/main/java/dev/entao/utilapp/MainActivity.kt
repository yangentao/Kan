package dev.entao.utilapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.entao.kan.base.StackActivity
import dev.entao.kan.base.toast

//import dev.entao.kan.log.logd

class MainActivity : StackActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentPage(MainPage())

    }


}
