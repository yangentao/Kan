package dev.entao.kan.pinyin

object PinYinText {
    val map = HashMap<String, String>(2048)
    private val buf = StringBuilder(64)

    @Synchronized
    fun find(text: String): String {
        var s: String? = map[text]
        if (s != null) {
            return s
        }
        s = makeSpell(text)
        map[text] = s
        return s
    }

    private fun makeSpell(name: String): String {
        if (name.isEmpty()) {
            return name
        }
        buf.clear()
        var type = 0
        for (ch in name) {
            if (ch == ' ') {
                val lastCh = buf.lastOrNull()
                if (lastCh != null) {
                    if (lastCh != ' ') {
                        buf.append(' ')
                    }
                }
                type = 0
                continue
            }
            val py = PinYin.findOne(ch)
            if (py != null) {
                if (type != 0) {
                    buf.append(' ')
                }
                buf.append(py)
                type = 1
                continue
            }

            if (ch.isLetter()) {
                if (type != 0 && type != 2) {
                    buf.append(' ')
                }
                buf.append(ch.toLowerCase())
                type = 2
                continue
            }

            if (type != 0 && type != 3) {
                buf.append(" #")
            }
            type = 3
        }
        return buf.toString()
    }
}