@file:Suppress("MemberVisibilityCanBePrivate", "ObjectLiteralToLambda")

package dev.entao.kan.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import dev.entao.kan.appbase.App
import dev.entao.kan.appbase.InMainThread
import dev.entao.kan.appbase.Task
import dev.entao.kan.appbase.ex.Bmp
import dev.entao.kan.appbase.ex.saveJpg
import dev.entao.kan.appbase.ex.savePng
import dev.entao.kan.base.ex.lowerCased
import dev.entao.kan.dialogs.HorProgressDlg
import dev.entao.kan.dialogs.SpinProgressDlg
import dev.entao.kan.dialogs.dialogX
import dev.entao.kan.ext.*
import dev.entao.kan.log.Yog
import dev.entao.kan.util.*
import dev.entao.kan.widget.RelativeLayoutX
import java.io.File
import kotlin.collections.set

/**
 * Created by entaoyang@163.com on 16/3/12.
 */


/**
 * 不要调用getActivity().finish(). 要调用finish(), finish处理了动画
 * fragment基类 公用方法在此处理
 */
open class BasePage : Fragment(), MsgListener {

    val uniqueName: String = "fragment${identity++}"

    lateinit var pageRootView: RelativeLayoutX
        private set

    @SuppressLint("UseSparseArrays")
    private val resultListeners = HashMap<Int, ActivityResultListener>(8)
    lateinit var spinProgressDlg: SpinProgressDlg
    lateinit var horProgressDlg: HorProgressDlg


    val watchMap = HashMap<Uri, ContentObserver>()

    lateinit var loadingView: ProgressBar

    fun showLoading() {
        if (InMainThread) {
            this.loadingView.bringToFront()
            this.loadingView.visiable()
        } else {
            Task.fore {
                this.loadingView.bringToFront()
                this.loadingView.visiable()
            }
        }
    }

    fun hideLoading() {
        if (InMainThread) {
            this.loadingView.gone()
        } else {
            Task.fore {
                this.loadingView.gone()
            }
        }
    }

    override fun onDestroyView() {
        pageRootView.removeAllViews()
        pageRootView.removeFromParent()
        for (ob in watchMap.values) {
            act.contentResolver.unregisterContentObserver(ob)
        }
        watchMap.clear()
        MsgCenter.remove(this)
        super.onDestroyView()
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        spinProgressDlg = SpinProgressDlg(act)
        horProgressDlg = HorProgressDlg(act)
        MsgCenter.listenAll(this)
        pageRootView = RelativeLayoutX(act)
        pageRootView.click { }

        loadingView = ProgressBar(context)
        loadingView.isIndeterminate = true
        loadingView.gone()
        pageRootView.addView(loadingView, RParam.Center.size(50))

        onCreatePage(act, pageRootView, savedInstanceState)
        return pageRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPageCreated()
    }

    open fun onCreatePage(context: Context, pageView: RelativeLayout, savedInstanceState: Bundle?) {

    }

    open fun onPageCreated() {

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Perm.onPermResult(requestCode)
    }


    fun statusBarColor(color: Int) {
        val w = activity?.window ?: return
        if (Build.VERSION.SDK_INT >= 21) {
            w.statusBarColor = color
        }
    }


    override fun onResume() {
        super.onResume()
        if (!isHidden) {
            onShow()
        }
    }

    override fun onPause() {
        if (!isHidden) {
            onHide()
        }
        Yog.flush()
        super.onPause()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (isResumed) {
            if (hidden) onHide() else onShow()
        }
    }

    open fun onShow() {

    }

    open fun onHide() {

    }

    /**
     * 可见, 并且没锁屏

     * @return
     */
    val isVisiableToUser: Boolean
        get() = this.isResumed && isVisible


    fun startActivityResultUri(intent: Intent, block: (Uri) -> Unit) {
        val ob = object : ActivityResultListener {
            override fun onResult(success: Boolean, intent: Intent?) {
                if (success && intent?.data != null) {
                    block(intent.data!!)
                }
            }
        }
        this.startActivityForResult(intent, ob)
    }

    fun startActivityResultOK(intent: Intent, block: (Intent?) -> Unit) {
        val ob = object : ActivityResultListener {
            override fun onResultOK(intent: Intent?) {
                block(intent)
            }
        }
        this.startActivityForResult(intent, ob)
    }

    fun startActivityResult(intent: Intent, block: (Boolean, Intent?) -> Unit) {
        val ob = object : ActivityResultListener {
            override fun onResult(success: Boolean, intent: Intent?) {
                block(success, intent)
            }
        }
        this.startActivityForResult(intent, ob)
    }

    fun startActivityForResult(intent: Intent, onResult: ActivityResultListener) {
        val requestCode = genRequestCode()
        resultListeners[requestCode] = onResult
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        resultListeners.remove(requestCode)?.onActivityResult(resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    fun finish() {
        val a = activity ?: return
        if (a is StackActivity) {
            a.pop()
        } else {
            a.finish()
        }
    }


    open fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return false
    }


    fun unWatch(uri: Uri) {
        val ob = watchMap[uri]
        if (ob != null) {
            act.contentResolver.unregisterContentObserver(ob)
        }
    }

    fun watch(uri: Uri, block: (Uri) -> Unit = {}) {
        if (watchMap.containsKey(uri)) {
            return
        }
        val ob = object : ContentObserver(Handler(Looper.getMainLooper())) {

            override fun onChange(selfChange: Boolean, uri: Uri) {
                mergeAction("watchUri:$uri") {
                    block(uri)
                    onUriChanged(uri)
                }
            }
        }
        watchMap[uri] = ob
        act.contentResolver.registerContentObserver(uri, true, ob)
    }

    open fun onUriChanged(uri: Uri) {

    }


    override fun onMsg(msg: Msg) {
    }


    companion object {

        private var identity: Int = 1
    }

}
