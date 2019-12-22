@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.entao.kan.page

import android.content.Context
import android.widget.Button
import android.widget.LinearLayout
import dev.entao.kan.base.BlockUnit
import dev.entao.kan.base.ColorX
import dev.entao.kan.base.Prop
import dev.entao.kan.creator.add
import dev.entao.kan.creator.button
import dev.entao.kan.creator.textView
import dev.entao.kan.ext.*
import dev.entao.kan.list.itemviews.*
import dev.entao.kan.theme.Space
import dev.entao.kan.widget.UserItemView


open class SettingPage : TitlePage() {
    init {
        enableContentScroll = true
    }

    override fun onCreateContent(context: Context, contentView: LinearLayout) {
        super.onCreateContent(context, contentView)
        rootLinearView.backColor(ColorX.backGray)

    }

    protected val itemParam: LinearLayout.LayoutParams get() = LParam.FillW.WrapH.marginBottom(1)

    fun userItem(): UserItemView {
        return contentView.add(UserItemView::class, itemParam) {
            backColorWhite()
        }
    }

    fun labelList(label: String, p: Prop): IntLabelListItemView {
        return contentView.labelListInt(itemParam) {
            this.label = label
            this.optionsFrom(p)
        }
    }

    fun labelSwitch(label: String): LabelSwitchItemView {
        return contentView.labelSwitch(itemParam) {
            this.label = label
        }
    }

    fun labelValue(label: String): LabelValueItemView {
        return contentView.labelValue(itemParam) {
            this.label = label
        }
    }

    fun labelImage(label: String): LabelImageItemView {
        return contentView.labelImage(itemParam) {
            this.label = label
//            this.image = Res.portrait.drawableRes
        }
    }

    fun group(text: String) {
        contentView.textView(LParam.FillW.WrapH) {
            this.text = text
            textColorMajor()
            textSizeB()
            gravityCenterVertical()
            paddingX(Space.X)
        }
    }

    fun buttonRed(label: String, block: BlockUnit): Button {
        return contentView.button(LParamButton.marginX(20)) {
            styleRedRound()
            this.text = label
            this.click(block)
        }
    }
}