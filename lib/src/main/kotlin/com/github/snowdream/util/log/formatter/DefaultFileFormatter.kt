package com.github.snowdream.util.log.formatter

import com.github.snowdream.util.log.LogItem
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by snowdream on 17/4/24.
 */
class DefaultFileFormatter : AbstractLogFormatter() {
    private var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault())

    override fun format(item: LogItem): String {
        val buffer: StringBuffer = StringBuffer()

        val myDate = Date()

        buffer.append(formatter.format(myDate))
        buffer.append("\t")
        buffer.append(getLevelString(item.level))
        buffer.append("/")
        buffer.append(item.tag)
        buffer.append(":")
        buffer.append("\t")
        buffer.append(item.msg)
        if (item.tr != null) {
            buffer.append("\n")
            buffer.append(getStackTraceString(item.tr))
        }

        return buffer.toString()
    }
}