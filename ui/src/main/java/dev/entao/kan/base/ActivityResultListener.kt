package dev.entao.kan.base

import android.app.Activity
import android.content.Intent

private var _reqCode: Int = 30000

//[30000, 31000]
fun genRequestCode(): Int {
    _reqCode += 1
    if (_reqCode > 31000) {
        _reqCode = 30000
    }
    return _reqCode
}

interface ActivityResultListener {
    fun onActivityResult(resultCode: Int, intent: Intent?) {
        val ok = resultCode == Activity.RESULT_OK
        onResult(ok, intent)
        if (ok) {
            onResultOK(intent)
        } else {
            onResultFailed(intent)
        }
    }

    fun onResult(success: Boolean, intent: Intent?) {

    }

    fun onResultOK(intent: Intent?) {

    }

    fun onResultFailed(intent: Intent?) {

    }
}