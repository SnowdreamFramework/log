package com.github.snowdream.util.log.filter

import com.github.snowdream.util.log.LogItem

/**
 * Log Filter
 *
 * Created by snowdream on 17/4/23.
 */
abstract class AbstractLogFilter {

    /**
     * if true, the log will be kept.
     * otherwise ,it will be skipped.
     *
     * @param item
     *
     * @return
     */
    abstract fun filter(item: LogItem): Boolean

}