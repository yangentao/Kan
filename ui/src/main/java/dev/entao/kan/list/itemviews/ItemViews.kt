package dev.entao.kan.list.itemviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Size
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import dev.entao.kan.appbase.dp
import dev.entao.kan.creator.imageView
import dev.entao.kan.creator.textView
import dev.entao.kan.ext.*
import dev.entao.kan.theme.Space


open class RelItemView(context: Context) : RelativeLayout(context) {
    var positionBind: Int = 0
    var argInt: Int = 0
    var argString: String = ""

    init {
        needId()
        padding(Space.X, 5, Space.X, 5)
        backColorWhiteFade()
        this.minimumHeight = 50.dp
        this.layoutParams = MParam.FillW.WrapH
    }
}


class ImageTitleItemView(context: Context) : RelItemView(context) {
    private var size: Size = Size(40, 40)
    val imageView: ImageView = imageView(RParam.size(size.width, size.height).Left.CenterY) {
        scaleCenterCrop()
    }
    val titleView: TextView = textView(RParam.Wrap.CenterY.toRight(imageView).marginLeft(8)) {
        singleLine().ellipsizeEnd()
        majorStyle()
    }

    init {
        padding(10, 5, Space.X, 5)
    }


    var title: String
        get() = titleView.textS
        set(value) {
            titleView.textS = value
        }


    var image: Drawable?
        get() = this.imageView.drawable
        set(value) {
            this.imageView.setImageDrawable(value)
        }

    var imageSize: Size
        get() = this.size
        set(value) {
            this.size = value
            val lp = imageView.layoutParams
            lp.size(size.width, size.height)
            this.minimumHeight = size.height.dp
        }
}




/**
 * -----------------------------------------------
 * |     | title                          time   |
 * |image|                                       |
 * |     | msg                            status |
 * -----------------------------------------------
 */
class ImageText4ItemView(context: Context) : RelItemView(context) {
    private var size: Size = Size(44, 44)
    val imageView: ImageView = imageView(RParam.size(size.width, size.height).Left.CenterY) {
        scaleCenterCrop()
    }
    val timeView: TextView = textView(RParam.Wrap.Top.Right) {
        singleLine().ellipsizeEnd()
        minorStyle()
    }
    val statusView: TextView = textView(RParam.Wrap.Bottom.Right) {
        singleLine().ellipsizeEnd()
        minorStyle()
    }
    val titleView: TextView = textView(RParam.WrapH.Top.toRight(imageView).toLeft(timeView).marginX(8)) {
        singleLine().ellipsizeEnd()
        majorStyle()
    }
    val msgView: TextView = textView(RParam.WrapH.Bottom.toRight(imageView).toLeft(statusView).marginX(8)) {
        singleLine().ellipsizeEnd()
        minorStyle()
    }

    init {
        padding(10, 8, Space.X, 8)
    }

    var titleValue: String
        get() = titleView.textS
        set(value) {
            titleView.textS = value
        }
    var msgValue: String
        get() = msgView.textS
        set(value) {
            msgView.textS = value
        }
    var statusValue: String
        get() = statusView.textS
        set(value) {
            statusView.textS = value
        }

    var timeValue: String
        get() = timeView.textS
        set(value) {
            timeView.textS = value
        }


    var image: Drawable?
        get() = this.imageView.drawable
        set(value) {
            this.imageView.setImageDrawable(value)
        }

    var imageSize: Size
        get() = this.size
        set(value) {
            this.size = value
            val lp = imageView.layoutParams
            lp.size(size.width, size.height)
            this.minimumHeight = size.height.dp
        }
}

/**
 * -----------------------------------------------
 * | title                          time   |
 * |                                       |
 * | msg                            status |
 * -----------------------------------------------
 */
class Text4ItemView(context: Context) : RelItemView(context) {
    val timeView: TextView = textView(RParam.Wrap.Top.Right) {
        singleLine().ellipsizeEnd()
        minorStyle()
    }
    val statusView: TextView = textView(RParam.Wrap.Bottom.Right) {
        singleLine().ellipsizeEnd()
        minorStyle()
    }
    val titleView: TextView = textView(RParam.Left.Top.WrapH.toLeft(timeView).marginRight(8).marginY(5)) {
        singleLine().ellipsizeEnd()
        majorStyle()
    }
    val msgView: TextView = textView(RParam.Left.Bottom.WrapH.toLeft(statusView).marginRight(8).marginY(0)) {
        singleLine().ellipsizeEnd()
        minorStyle()
    }

    init {
        padding(10, 8, Space.X, 8)
    }

    var titleValue: String
        get() = titleView.textS
        set(value) {
            titleView.textS = value
        }
    var msgValue: String
        get() = msgView.textS
        set(value) {
            msgView.textS = value
        }
    var statusValue: String
        get() = statusView.textS
        set(value) {
            statusView.textS = value
        }

    var timeValue: String
        get() = timeView.textS
        set(value) {
            timeView.textS = value
        }


}
