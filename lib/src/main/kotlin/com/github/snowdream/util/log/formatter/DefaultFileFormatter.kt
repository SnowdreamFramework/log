package com.github.snowdream.util.log.formatter

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by snowdream on 17/4/24.
 */
class DefaultFileFormatter  : AbstractLogFormatter(){
    private var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.fff", Locale.getDefault())

    override fun format(level: Int, tag: String, msg: String, tr: Throwable?): String {
        val buffer:StringBuffer = StringBuffer()

        val myDate = Date()

        buffer.append(formatter.format(myDate))
        buffer.append("\t")
        buffer.append(getLevelString(level))
        buffer.append("/")
        buffer.append(tag)
        buffer.append(":")
        buffer.append("\t")
        buffer.append(msg)
        buffer.append("\n")
        buffer.append(getStackTraceString(tr))

        return buffer.toString()
    }
}