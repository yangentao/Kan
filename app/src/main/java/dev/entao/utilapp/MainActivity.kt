package dev.entao.utilapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//import dev.entao.kan.log.logd

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val p = Person()
        p.name = "Yang"
        println("姓名: ${p.name}")

    }


    override fun finish() {
        super.finish()
//        logd("MainActivity", "finish()")
    }


}
