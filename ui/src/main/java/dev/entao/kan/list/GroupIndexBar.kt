package dev.entao.kan.list


import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import dev.entao.kan.appbase.*
import dev.entao.kan.base.ColorX
import dev.entao.kan.creator.createTextView
import dev.entao.kan.creator.textView
import dev.entao.kan.ext.*
import dev.entao.kan.log.logd


class GroupIndexBar(context: Context) : LinearLayout(context) {
    var labelColor: Int = ColorX.textPrimary
    private var items: List<String> = emptyList()
    private var currentLabel: String? = null
    private var feedbackView: TextView

    var onLabelChanged: (String) -> Unit = {
        logd(it)
    }

    private val touchListener = View.OnTouchListener { _, event ->
        val action = event.actionMasked
        val y = event.y.toInt()
        if (action == MotionEvent.ACTION_DOWN) {
//            this.isSelected = true
            selectByY(y)
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
            || action == MotionEvent.ACTION_OUTSIDE
        ) {
//            this.isSelected = false
            selectByY(y)
        } else if (action == MotionEvent.ACTION_MOVE) {
            selectByY(y)
        }
        false
    }


    init {
        this.vertical()
        this.clickable()
        this.setOnTouchListener(this.touchListener)

        val d = ShapeRect(colorParse("#555"), 10).stroke(2, colorParse("#ddd")).value
        feedbackView = createTextView()
        feedbackView.textColor(Color.WHITE).textSizeSp(50).gravityCenter().backDrawable(d).gone()
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun selectByY(y: Int) {
        for (i in 0 until childCount) {
            val itemView = getChildAt(i)
            if (y >= itemView.top && y <= itemView.bottom) {
                val s = itemView.tag as? String
                if (s != null && s != this.currentLabel) {
                    this.currentLabel = s
                    Task.mergeX("label_changed", 50) {
                        fireChanged()
                    }
                }
            }
        }
        updateLabelSelectState()
    }

    private fun fireChanged() {
        val cc = this.currentLabel ?: return
        this.onLabelChanged(cc)
        if (feedbackView.parentGroup == null) {
            val rl = this.parentGroup as? RelativeLayout ?: return
            rl.addView(feedbackView, RParam.Center.size(70))
        }
        feedbackView.text = cc
        feedbackView.visiable()
        Task.merge("hide_feedback_view", 650) {
            feedbackView.gone()
        }
    }


    fun setLabelItems(items: List<String>) {
        this.items = items
        this.rebuild()
    }

    private fun rebuild() {
        this.removeAllViews()
        for (s in items) {
            this.textView(LParam.FillW.HeightFlex.Center) {
                this.tag = s
                this.text(s).textSizeD().gravityCenter()
                this.typeface = Typeface.MONOSPACE
                this.textColorList(labelColor) {
                    selected(Color.WHITE)
                }
                this.backColorList(Color.TRANSPARENT) {
                    selected(Color.GRAY)
                }
            }
        }
        if (this.items.isEmpty()) {
            this.gone()
        } else {
            this.visiable()
        }
    }

    fun setCurrentLabel(label: String) {
        this.currentLabel = label
        Task.mergeX("set_curr_label", 20) {
            updateLabelSelectState()
        }
    }

    private fun updateLabelSelectState() {
        val lb = this.currentLabel ?: "__"
        for (i in 0 until childCount) {
            val v = getChildAt(i)
            v.isSelected = v.tag == lb
        }
    }

    companion object {
        const val WIDTH_PREFER = 30
    }

}
