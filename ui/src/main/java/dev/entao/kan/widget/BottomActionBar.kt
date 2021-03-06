@file:Suppress("unused", "MemberVisibilityCanBePrivate", "FunctionName")

package dev.entao.kan.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import dev.entao.kan.appbase.*
import dev.entao.kan.base.BlockUnit
import dev.entao.kan.base.ColorX
import dev.entao.kan.creator.createImageView
import dev.entao.kan.creator.createTextView
import dev.entao.kan.ext.*


class BottomActionBar(context: Context) : LinearLayout(context) {
    val items = ArrayList<BarItemData>()
    var itemColor: Int = ColorX.textPrimary
    var topLine: Boolean = true
    private val paintLine = Paint(0)

    init {
        needId()
        horizontal()
        styleWhite()
        this.layoutParams = MParam.FillW.height(HEIGHT)
    }

    fun styleBlue() {
        itemColor = Color.WHITE
        this.backColor(ColorX.blue)
    }

    fun styleWhite() {
        itemColor = ColorX.textPrimary
        this.backColor(Color.WHITE)
    }

    fun style(foreColor: Int, backColor: Int) {
        itemColor = foreColor
        this.backColor(backColor)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (topLine) {
            paintLine.style = Paint.Style.FILL
            paintLine.color = ColorX.lineGray
            val rectLine = Rect(0, 0, this.width, 1)
            canvas.drawRect(rectLine, paintLine)
        }
    }

    operator fun invoke(block: BottomActionBar.() -> Unit) {
        this.block()
        commit()
    }

    fun add(item: BarItemData) {
        items += item
    }

    fun commit() {
        removeAllViews()

        for (item in items) {
            val v = makeView(item)
            this.addView(v, LParam.WidthFlex.FillH.Center)
            v.setOnClickListener {
                item.onAction()
            }
            v.background = listColorDrawable(Color.TRANSPARENT) {
                pressed(ColorX.fade)
                checked(ColorX.fade)
                selected(ColorX.fade)
                disabled(ColorX.backDisabled)
            }
        }

    }

    private val BarItemData.draw: Drawable?
        get() {
            var d: Drawable? = this.drawable
            if (d == null && this.drawResId != 0) {
                d = this.drawResId.resDrawable
            }
            return d
        }

    private fun makeView(item: BarItemData): View {
        val d: Drawable? = item.draw
        val rl = RelativeLayout(context).needId()
        if (item.label.isEmpty()) {
            val v = createImageView()
            v.scaleCenterInside()
            if (d != null) {
                v.setImageDrawable(d.tinted(itemColor))
            }
            rl.addView(v, RParam.size(36).Center)
        } else {
            if (d == null) {
                val v = createTextView()
                v.gravityCenter()
                v.setTextColor(itemColor)
                v.text = item.label
                v.textSizeB()
                rl.addView(v, RParam.Wrap.Center)
            } else {
                val v = createTextView()
                v.gravityCenter()
                v.setTextColor(itemColor)
                v.text = item.label
                val dd = d.sizeH(24).tinted(itemColor)
                v.compoundDrawablePadding = 2.dp
                v.setCompoundDrawables(null, dd, null, null)
                v.textSizeD()
                rl.addView(v, RParam.Wrap.Center)
            }
        }
        return rl
    }

    infix fun String.ON(block: BlockUnit): BarItemData {
        val b = BarItemData()
        b.label = this
        b.onAction = block
        add(b)
        return b
    }

    infix fun Drawable.ON(block: BlockUnit): BarItemData {
        val b = BarItemData()
        b.drawable = this
        b.onAction = block
        add(b)
        return b
    }

    infix fun Int.ON(block: BlockUnit): BarItemData {
        val b = BarItemData()
        b.drawResId = this
        b.onAction = block
        add(b)
        return b
    }

    infix fun Pair<String, Int>.ON(block: BlockUnit): BarItemData {
        val b = BarItemData()
        b.label = this.first
        b.drawResId = this.second
        b.onAction = block
        add(b)
        return b
    }

    companion object {
        const val HEIGHT = 64// dp
    }
}

val <T : ViewGroup.LayoutParams> T.HeightBottomActionBar: T
    get() {
        return height(BottomActionBar.HEIGHT)
    }

fun Drawable.sizeX(width: Int, height: Int): Bitmap {
    val w = width.dp
    val h = height.dp
    if (this is BitmapDrawable) {
        return this.bitmap.scaleTo(w, h)
    }

    val b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(b)
    this.setBounds(0, 0, w, h)
    this.draw(canvas)
    return b
}