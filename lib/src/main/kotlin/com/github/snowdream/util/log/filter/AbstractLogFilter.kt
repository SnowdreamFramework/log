package com.github.snowdream.util.log.filter

/**
 * Log Filter
 *
 * Created by snowdream on 17/4/23.
 */
abstract class AbstractLogFilter{

    /**
     * if true, the log will be kept.
     * otherwise ,it will be skipped.
     *
     * @param level
     * *
     * @param tag
     * *
     * @param msg
     * *
     * @return
     */
    abstract fun filter(level: Int, tag: String, msg: String): Boolean

}