package com.github.snowdream.util.log.formatter

import com.github.snowdream.util.log.LogItem

/**
 * Default log formatter for console and file
 *
 * Created by snowdream on 17/4/24.
 */
class DefaultFormatter : AbstractLogFormatter() {

    override fun format(item: LogItem): String {
        if (item.tr == null) {
            return item.msg
        } else {
            return item.msg + '\n' + getStackTraceString(item.tr)
        }
    }
}