package com.github.snowdream.util.log

import com.github.snowdream.util.log.filter.AbstractLogFilter
import com.github.snowdream.util.log.processor.AbstractLogProcessor

/**
 * Log Option
 *
 * Created by snowdream on 17/4/23.
 */
data class LogOption(val logProcessors: List<AbstractLogProcessor>, val logFilters: List<AbstractLogFilter> ?= null)