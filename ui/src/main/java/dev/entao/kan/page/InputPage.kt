package dev.entao.kan.page

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.*
import dev.entao.kan.appbase.Task
import dev.entao.kan.appbase.shapeRect
import dev.entao.kan.base.*
import dev.entao.kan.creator.*
import dev.entao.kan.dialogs.dialogX
import dev.entao.kan.ext.*
import dev.entao.kan.list.itemviews.TextDetailView
import dev.entao.kan.list.itemviews.textDetail
import dev.entao.kan.log.logd
import dev.entao.kan.base.ImageDef
import dev.entao.kan.theme.ViewSize
import dev.entao.kan.util.TimeDown
import java.util.regex.Pattern
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1

/**
 * Created by entaoyang@163.com on 2016-10-20.
 */

abstract class InputPage : TitlePage() {

    lateinit var inputLayout: LinearLayout

    var INPUT_HEIGHT = ViewSize.EditHeight
    var inputMarginTop = 10
    var buttonMarginTop = 30

    private val editList = ArrayList<Triple<String, EditText, KProperty<*>?>>()
    private val checkMap = HashMap<String, CheckBox>()
    private val dateMap = HashMap<String, TextDetailView>()
    private val dateFormatMap = HashMap<String, String>().withDefault { MyDate.FORMAT_DATE }
    private val selectMap = HashMap<String, TextDetailView>()
    private val validMap = LinkedHashMap<String, InputValid>()
    private var codeEdit: EditText? = null
    private var codeButton: Button? = null
    private var timeDownKey: String = System.currentTimeMillis().toString()
    private var codeClickTime: Long = 0

    init {
        enableContentScroll = true
    }

    override fun onDestroyView() {
        editList.clear()
        checkMap.clear()
        dateFormatMap.clear()
        selectMap.clear()
        validMap.clear()
        codeEdit?.removeFromParent()
        codeButton?.removeFromParent()
        codeEdit = null
        codeButton = null
        super.onDestroyView()
    }

    fun fromEdit(model: Any, ps: List<KMutableProperty1<*, *>>) {
        for (p in ps) {
            val s = getText(p.userName)
            val v: Any = strToV(s, p) ?: defaultValueOfProperty(p)
            p.setValue(model, v)
        }
    }


    fun edit(block: InputOption.() -> Unit = {}): EditText {
        val io = InputOption()
        io.height = ViewSize.EditHeight
        io.block()
        if (io.inputValid.label.isEmpty()) {
            io.inputValid.label(io.hint)
        }
        val ed = inputLayout.editX(
            LParam.FillW.height(io.height).margins(
                io.marginLeft,
                io.marginTop,
                io.marginRight,
                io.marginBottom
            )
        ) {
            padding(5, 2, 5, 2)
            var hintStr = io.hint
            if (io.inputValid.require) {
                if (!hintStr.endsWith('*')) {
                    hintStr += "*"
                }
            }
            hint = hintStr
            setText(io.value)
        }
        editList.add(Triple(io.key, ed, io.prop))
        validMap[io.key] = io.inputValid
        return ed
    }

    fun phone(block: InputOption.() -> Unit = {}): EditText {
        val ed = edit {
            hint = "请输入手机号"
            valid {
                phone11()
            }
            this.block()
        }
        ed.inputTypePhone()
        return ed
    }

    fun email(block: InputOption.() -> Unit = {}): EditText {
        val ed = edit {
            hint = "请输入邮箱"
            valid {
                email()
            }
            this.block()
        }
        ed.inputTypeEmail()
        return ed
    }

    fun number(block: InputOption.() -> Unit = {}): EditText {
        val ed = edit {
            valid {
                numbers()
            }
            this.block()
        }
        ed.inputTypeNumber()
        return ed
    }

    fun password(block: InputOption.() -> Unit = {}): EditText {
        val ed = edit {
            hint = "请输入密码"
            valid {
                notEmpty()
            }
            this.block()
        }
        ed.inputTypePassword()
        return ed
    }

    fun passwordAgain(block: InputOption.() -> Unit = {}): EditText {
        val ed = edit {
            hint = "请再次输入密码"
            valid {
                notEmpty()
            }
            this.block()
        }
        ed.inputTypePassword()
        return ed
    }

    fun passwordNumber(block: InputOption.() -> Unit = {}): EditText {
        val ed = edit {
            hint = "请输入数字密码"
            valid {
                notEmpty()
                numbers()
            }
            this.block()
        }
        ed.inputTypePasswordNumber()
        return ed
    }

    fun passwordNumberAgain(block: InputOption.() -> Unit = {}): EditText {
        val ed = edit {
            hint = "请再次输入数字密码"
            valid {
                notEmpty()
                numbers()
            }
            this.block()
        }
        ed.inputTypePasswordNumber()
        return ed
    }

    fun checkbox(block: InputOption.() -> Unit = {}): CheckBox {
        val io = InputOption()
        io.height = ViewGroup.LayoutParams.WRAP_CONTENT
        io.block()
        if (io.inputValid.label.isEmpty()) {
            io.inputValid.label(io.hint)
        }
        io.inputValid.label(io.hint)
        val cb = inputLayout.checkBox(
            LParam.FillW.height(io.height).margins(
                io.marginLeft,
                io.marginTop,
                io.marginRight,
                io.marginBottom
            )
        ) {
            padding(10, 5, 5, 5)
            this.hint = io.hint
            if (io.value.isEmpty()) {
                this.text = io.hint
            } else {
                this.text = io.value
            }
        }
        checkMap[io.key] = cb
        validMap[io.key] = io.inputValid
        return cb
    }

    fun static(label: String, marginTop: Int = inputMarginTop): TextView {
        return inputLayout.textView(
            LParam.FillW.height(INPUT_HEIGHT).margins(
                0,
                marginTop,
                0,
                0
            )
        ) {
            text = label
        }
    }

    fun label(label: String, marginTop: Int = inputMarginTop): TextView {
        return inputLayout.textView(LParam.FillW.WrapH.margins(0, marginTop, 0, 0)) {
            text = label
        }
    }

    fun image(
        label: String,
        imgHeight: Int = INPUT_HEIGHT,
        marginTop: Int = inputMarginTop
    ): TextView {
        return inputLayout.textView(
            LParam.FillW.height(imgHeight).margins(
                0,
                marginTop,
                0,
                0
            )
        ) {
            text = label
            rightImage(ImageDef.imageMiss, imgHeight)
        }
    }

    fun button(key: String, title: String, marginTop: Int = buttonMarginTop): Button {
        val b = inputLayout.button(LParam.FillW.HeightButton.margins(0, marginTop, 0, 6)) {
            setOnClickListener { _onButtonClick(key) }
            text = title
        }
        return b
    }

    fun buttonSafe(key: String, title: String, marginTop: Int = buttonMarginTop): Button {
        return button(key, title, marginTop).styleGreen()
    }

    fun buttonRed(key: String, title: String, marginTop: Int = buttonMarginTop): Button {
        return button(key, title, marginTop).styleRed()
    }

    fun buttonWhite(key: String, title: String, marginTop: Int = buttonMarginTop): Button {
        return button(key, title, marginTop).styleWhite()
    }

    fun textDetail(title: String, marginTop: Int = inputMarginTop): TextDetailView {
        return inputLayout.textDetail(
            LParam.FillW.height(INPUT_HEIGHT).margins(
                0,
                marginTop,
                0,
                0
            )
        ) {
            padding(0, 0, 0, 0)
            detailView.textSizeB().gravityCenter().padding(10, 5, 10, 5)
            detailView.miniWidthDp(100)
            detailView.miniHeightDp(ViewSize.ButtonHeightSmall - 10)
            textView.text = title
        }
    }


    @SuppressLint("SetTextI18n")
    fun selectMap(p: Prop1, optMap: Map<Any, String>): TextDetailView {
        val v = textDetail(p.userLabel)
        if (p.hasAnnotation<Required>()) {
            v.textView.text = p.userLabel + "*"
        }
        v.detailView.background = backVal
        selectMap[p.userName] = v
        v.clickView {
            this.dialogX.showListItem(optMap.keys.toList(), null, { optMap[it] ?: "" }) {
                v.tag = it
                v.detailValue = optMap[it]
                logd("SelectItem: tag = ", v.tag, " val=", optMap[it])
            }
        }
        return v
    }

    val backVal: Drawable
        get() {
            return shapeRect {
                fill(ColorX.TRANS)
                corner(3)
                stroke(1, ColorX.lineGray)
            }
        }

    fun select(p: Prop1): TextDetailView {
        return selectMap(p, p.selectOptionsStatic.mapKeys { it.key as Any })
    }

    fun select(
        p: KProperty1<*, *>,
        items: Collection<Any>,
        displayProp: KProperty1<*, *>,
        idProp: KProperty1<*, *>
    ): TextDetailView {
        val v = textDetail(p.userLabel)
        if (p.hasAnnotation<Required>()) {
            v.textView.text = p.userLabel + "*"
        }
        v.detailView.background = backVal
        selectMap[p.userName] = v
        v.clickView {
            this.dialogX.showListAny(
                items.toList(),
                null,
                { displayProp.getValue(it)?.toString() ?: "" }) {
                v.tag = idProp.getValue(it)
                v.detailValue = displayProp.getValue(it)?.toString()
            }
        }
        return v
    }

    fun select(key: String, title: String, value: String, items: List<String>): TextDetailView {
        val v = textDetail(title)
        v.detailView.background = backVal
        selectMap[key] = v
        v.detailValue = value
        v.tag = value
        v.clickView {
            this.dialogX.showListItem(items, null) { item ->
                v.tag = item
                v.detailValue = item
            }

        }
        return v
    }

    fun selectPair(p: KProperty<*>, items: List<Pair<String, String>>): TextDetailView {
        val v = if (p is KProperty0<*>) p.getValue()?.toString() ?: "" else ""
        val lb = if (p.hasAnnotation<Required>()) {
            p.userLabel + "*"
        } else {
            p.userLabel
        }
        return selectPair(p.userName, lb, v, items)
    }

    fun selectPair(
        key: String,
        title: String,
        value: String,
        items: List<Pair<String, String>>
    ): TextDetailView {
        val v = textDetail(title)
        v.detailView.background = backVal
        selectMap[key] = v
        val item = items.find { it.first == value }
        v.detailValue = item?.second
        v.tag = item?.first
        v.clickView {
            this.dialogX.showListItemN(items.map { it.second }, null) {
                v.tag = items[it].first
                v.detailValue = items[it].second
            }

        }
        return v
    }

    fun valid(): Boolean {
        for ((k, v) in validMap.entries) {
            val ed = editList.find { it.first == k }!!.second
            val info = v.checkAll(ed)
            if (info != null) {
                this.dialogX.showAlert(info)
                return false
            }

        }
        return true
    }


    override fun onCreateContent(context: Context, contentView: LinearLayout) {
        inputLayout = contentView.linearVer(LParam.FillW.WrapH) {
            padding(30, 25, 30, 20)
        }
    }

    override fun onContentCreated() {
        super.onContentCreated()
        val sz = editList.size
        editList.forEachIndexed { n, p ->
            if (n < sz - 1) {
                p.second.imeNext {
                    editList[n + 1].second.requestFocus()
                }
            } else {
                p.second.imeDone()
            }
        }
    }


    //中国的11位手机号码格式, 连续11位数字,1开头
    fun isPhoneFormatCN11(s: String?): Boolean {
        val ss = s ?: return false
        val regex = "1[0-9]{10}"
        val p = Pattern.compile(regex)
        val m = p.matcher(ss)
        return m.find()
    }

    fun isCheck(key: String): Boolean {
        return checkMap[key]?.isChecked ?: false
    }

    fun setCheck(key: String, check: Boolean) {
        checkMap[key]?.isChecked = check
    }

    val code: String
        get() {
            return codeEdit?.text?.toString() ?: ""
        }

    fun getText(key: String): String {
        val s = editList.find { it.first == key }?.second?.text?.toString() ?: ""
        val v = validMap[key]
        if (v != null && v.trimText) {
            return s.trim()
        }
        return s
    }

    fun setText(key: String, text: String) {
        editList.find { it.first == key }?.second?.setText(text)
    }

    fun disableEdit(key: String) {
        editList.find { it.first == key }?.second?.isEnabled = false
    }

    fun getDate(key: String): Long {
        val s = dateMap[key]!!.detailView.text!!.toString()
        if (s.isNotEmpty()) {
            return MyDate.parse(dateFormatMap[key] ?: MyDate.FORMAT_DATE, s)!!.time
        }
        return 0
    }

    fun setDate(key: String, date: Long) {
        dateMap[key]?.detailValue = MyDate(date).format(dateFormatMap[key] ?: MyDate.FORMAT_DATE)
    }


    fun getSelectValue(key: String): String {
        return selectMap[key]?.tag as? String ?: ""
    }

    fun startTimeDown(seconds: Int) {
        Task.fore {
            TimeDown.startClick(timeDownKey, seconds, codeButton!!)
        }
    }


    fun addVerifyCode(phoneEditKey: String, marginTop: Int = inputMarginTop) {

        inputLayout.linearHor(LParam.FillW.WrapH.margins(0, marginTop, 0, 0)) {
            codeEdit = edit(LParam.FlexX.HeightEdit) {
                hint = "输入验证码"
                inputTypeNumber()
            }
            codeButton = button(LParam.WrapW.HeightButtonSmall.margins(3, 0, 0, 0)) {
                text = "获取验证码"
                style {
                    outlineBlue()
                }
            }
        }
        TimeDown.updateView(this.timeDownKey, codeButton!!)
        codeButton?.clickView {
            codeClickTime = System.currentTimeMillis()
            val phone = getText(phoneEditKey)
            if (!isPhoneFormatCN11(phone)) {
                toast("手机号格式错误")
            } else {
                startTimeDown(60)
                onCodeButtonClick(phone)
            }
        }
    }

    open fun onCodeButtonClick(phone: String) {

    }


    private fun _onButtonClick(key: String) {
        hideInputMethod()
        onButtonClick(key)
    }

    abstract fun onButtonClick(key: String)
}