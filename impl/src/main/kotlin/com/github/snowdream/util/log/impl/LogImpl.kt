package com.github.snowdream.util.log.impl

import android.content.Context
import android.text.TextUtils
import com.github.snowdream.toybricks.annotation.Implementation
import com.github.snowdream.util.log.ILog
import com.github.snowdream.util.log.Log
import com.github.snowdream.util.log.LogItem
import com.github.snowdream.util.log.LogOption
import com.github.snowdream.util.log.processor.AbstractLogProcessor
import com.github.snowdream.util.log.processor.LogConsoleProcessor
import com.github.snowdream.util.log.processor.LogFileProcessor
import com.github.snowdream.util.log.transform.AbstractLogTransform
import com.github.snowdream.util.log.transform.DefaultJsonTransform
import com.github.snowdream.util.log.transform.DefaultXmlTransform
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by snowdream on 17/4/22.
 */
@Implementation(ILog::class)
class LogImpl : ILog {
    private lateinit var mOption: LogOption

    private val mSingleThreadExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private val mXmlTransform: DefaultXmlTransform = DefaultXmlTransform()

    private val mJsonTransform: DefaultJsonTransform = DefaultJsonTransform()


    private val LOG_MAX_LENGTH: Int = 4 * 1000

    override fun setOption(option: LogOption) {
        this.mOption = option
    }

    override fun setOption(context: Context, console: Boolean, file: Boolean) {
        val logProcessors: MutableList<AbstractLogProcessor> = mutableListOf()

        if (console) {
            val logConsoleProcessor: LogConsoleProcessor = LogConsoleProcessor(context)
            logProcessors.add(logConsoleProcessor)
        }

        if (file) {
            val logFileProcessor: LogFileProcessor = LogFileProcessor(context)
            logProcessors.add(logFileProcessor)
        }

        this.mOption = LogOption(logProcessors)
    }

    override fun v(tag: String, msg: String, tr: Throwable?) {
        process(Log.VERBOSE, tag, msg, tr)
    }

    override fun v(tag: String, msg: String) {
        process(Log.VERBOSE, tag, msg, null)
    }

    override fun d(tag: String, msg: String, tr: Throwable?) {
        process(Log.DEBUG, tag, msg, tr)
    }

    override fun d(tag: String, msg: String) {
        process(Log.DEBUG, tag, msg, null)
    }

    override fun i(tag: String, msg: String, tr: Throwable?) {
        process(Log.INFO, tag, msg, tr)
    }

    override fun i(tag: String, msg: String) {
        process(Log.INFO, tag, msg, null)
    }

    override fun w(tag: String, msg: String, tr: Throwable?) {
        process(Log.WARN, tag, msg, tr)
    }

    override fun w(tag: String, msg: String) {
        process(Log.WARN, tag, msg, null)
    }

    override fun isLoggable(tag: String, level: Int): Boolean {
        return android.util.Log.isLoggable(tag, level)
    }

    override fun w(tag: String, tr: Throwable) {
        w(tag, "", tr)
    }

    override fun e(tag: String, msg: String, tr: Throwable?) {
        process(Log.ERROR, tag, msg, tr)
    }

    override fun e(tag: String, msg: String) {
        process(Log.ERROR, tag, msg, null)
    }

    override fun wtf(tag: String, tr: Throwable) {
        wtf(tag, "", tr)
    }

    override fun wtf(tag: String, msg: String, tr: Throwable?) {
        process(Log.WTF, tag, msg, tr)
    }

    override fun wtf(tag: String, msg: String) {
        process(Log.WTF, tag, msg, null)
    }

    override fun json(tag: String, msg: String) {
        obj(tag, msg, mJsonTransform)
    }

    override fun xml(tag: String, msg: String) {
        obj(tag, msg, mXmlTransform)
    }

    override fun obj(tag: String, obj: Any, transform: AbstractLogTransform) {
        process(Log.INFO, tag, "", null, obj, transform)
    }

    /**
     * process the log
     */
    private fun process(level: Int, tag: String, msg: String, tr: Throwable?) {
        process(level, tag, msg, tr, null, null)
    }

    /**
     * process the log
     */
    private fun process(level: Int, tag: String, msg: String, tr: Throwable?, obj: Any?, transform: AbstractLogTransform?) {
        mSingleThreadExecutor.execute({
            var _msg = msg

            if (_msg.length <= LOG_MAX_LENGTH) {
                //log transform
                if (TextUtils.isEmpty(_msg) && obj != null && transform != null) {
                    try {
                        _msg = transform.transform(obj)
                    } catch(e: Exception) {
                        e.printStackTrace()
                    }
                }

                //log Processors
                val item: LogItem = LogItem(level, tag, _msg, tr)
                val logProcessors: List<AbstractLogProcessor> = mOption.logProcessors

                logProcessors.forEach {
                    it.process(item)
                }
            } else {
                var log: String = _msg

                while (log.length > LOG_MAX_LENGTH) {
                    val trunk: String = log.substring(0, LOG_MAX_LENGTH)
                    process(level, tag, trunk, tr)

                    log = log.substring(LOG_MAX_LENGTH)
                }

                process(level, tag, log, tr)
            }
        })

    }

    override fun getStackTraceString(tr: Throwable?): String {
        return getStackTraceString(tr)
    }

    override fun println(priority: Int, tag: String, msg: String) {
        android.util.Log.println(priority, tag, msg)
    }
}