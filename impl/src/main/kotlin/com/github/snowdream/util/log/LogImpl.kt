package com.github.snowdream.util.log

import android.content.Context
import com.github.snowdream.toybricks.annotation.Implementation
import com.github.snowdream.util.log.filter.AbstractLogFilter
import com.github.snowdream.util.log.processor.AbstractLogProcessor
import com.github.snowdream.util.log.processor.LogConsoleProcessor
import com.github.snowdream.util.log.processor.LogFileProcessor

/**
 * Created by snowdream on 17/4/22.
 */
@Implementation(ILog::class)
class LogImpl : ILog {
    private lateinit var option: LogOption

    override fun setOption(option: LogOption) {
        this.option = option
    }

    override fun setOption(context: Context, console: Boolean, file: Boolean) {
        val logProcessors: MutableList<AbstractLogProcessor> = mutableListOf()

        if (console){
            val logConsoleProcessor: LogConsoleProcessor = LogConsoleProcessor(context)
            logProcessors.add(logConsoleProcessor)
        }

        if (file){
            val logFileProcessor: LogFileProcessor = LogFileProcessor(context)
            logProcessors.add(logFileProcessor)
        }

        this.option = LogOption(logProcessors)
    }

    override fun v(tag: String, msg: String, tr: Throwable?) {
        process(Log.VERBOSE,tag,msg,tr)
    }

    override fun d(tag: String, msg: String, tr: Throwable?) {
        process(Log.DEBUG,tag,msg,tr)
    }

    override fun i(tag: String, msg: String, tr: Throwable?) {
        process(Log.INFO,tag,msg,tr)
    }

    override fun w(tag: String, msg: String, tr: Throwable?) {
        process(Log.WARN,tag,msg,tr)
    }

    override fun isLoggable(tag: String, level: Int): Boolean {
        return android.util.Log.isLoggable(tag, level)
    }

    override fun w(tag: String, tr: Throwable) {
        w(tag, "", tr)
    }

    override fun e(tag: String, msg: String, tr: Throwable?) {
        process(Log.ERROR,tag,msg,tr)
    }

    override fun wtf(tag: String, tr: Throwable) {
        wtf(tag, "", tr)
    }

    override fun wtf(tag: String, msg: String, tr: Throwable?) {
        process(Log.WTF,tag,msg,tr)
    }

    /**
     * process the log
     */
    fun process(level: Int, tag: String, msg: String, tr: Throwable?){
        //log Filters
        val logFilters: List<AbstractLogFilter> ?= option.logFilters

        if (logFilters != null){
            logFilters
                    .filterNot { it.filter(level ,tag ,msg) }
                    .forEach { return }
        }

        //log Processors
        val item: LogItem = LogItem(level, tag, msg, tr)
        val logProcessors: List<AbstractLogProcessor> = option.logProcessors

        logProcessors.forEach{
            it.process(item)
        }
    }

    override fun getStackTraceString(tr: Throwable?): String {
        return getStackTraceString(tr)
    }

    override fun println(priority: Int, tag: String, msg: String) {
        android.util.Log.println(priority, tag, msg)
    }
}