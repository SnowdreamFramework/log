package com.github.snowdream.util.log

/**
 * Created by snowdream on 17/4/25.
 */
data class LogItem(val level: Int, val tag: String, val msg: String, val tr: Throwable? = null)

