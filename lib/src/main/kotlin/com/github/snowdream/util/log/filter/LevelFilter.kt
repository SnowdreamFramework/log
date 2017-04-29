package com.github.snowdream.util.log.filter

import com.github.snowdream.util.log.Log
import com.github.snowdream.util.log.LogItem

/**
 * Created by snowdream on 17/4/24.
 */
class LevelFilter : AbstractLogFilter {
    private var level: Int = Log.INFO

    constructor(level: Int = Log.INFO) {
        if (level < Log.VERBOSE || level > Log.WTF) {
            throw IllegalArgumentException("The log level is invalid")
        }

        this.level = level
    }

    override fun filter(item: LogItem): Boolean {
        return item.level >= this.level
    }
}