package dev.entao.viewpage

import android.content.Context
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class ViewPage(context: Context) : RelativeLayout(context) {
    lateinit var activity: AppCompatActivity

    fun attach(act: AppCompatActivity) {
        this.activity = act
        act.lifecycle
    }

    fun onCreate() {

    }

    fun onStart() {

    }

    fun onResume() {

    }

    fun onStop() {

    }

    fun onPause() {

    }

    fun onDestroy() {

    }

    fun onDetach() {

    }
}