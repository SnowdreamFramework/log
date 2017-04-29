package com.github.snowdream.util.log.processor

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import com.github.snowdream.util.log.Log
import com.github.snowdream.util.log.LogItem
import com.github.snowdream.util.log.filter.AbstractLogFilter
import com.github.snowdream.util.log.formatter.AbstractLogFormatter
import com.github.snowdream.util.log.formatter.DefaultFormatter
import com.github.snowdream.util.log.generator.AbstractFilePathGenerator
import com.github.snowdream.util.log.generator.DefaultFilePathGenerator

/**
 * Log Console Processor
 *
 * Created by snowdream on 17/4/24.
 */
class LogConsoleProcessor : AbstractLogProcessor {

    constructor(context: Context) : super(context) {
        mLogFormatter = DefaultFormatter()
        mFilePathGenerator = DefaultFilePathGenerator(context, "app", ".log")

        mHandlerThread = HandlerThread("LogConsoleProcessor")
        mHandlerThread.start()

        mHandler = Handler(mHandlerThread.looper, this)
    }

    constructor(context: Context, logFormatter: AbstractLogFormatter, filePathGenerator: AbstractFilePathGenerator, logFilters: List<AbstractLogFilter>? = null) : super(context, logFormatter, filePathGenerator, logFilters) {
        mHandlerThread = HandlerThread("LogConsoleProcessor")
        mHandlerThread.start()

        mHandler = Handler(mHandlerThread.looper, this)
    }

    override fun process(item: LogItem) {
        //log Filters
        if (mLogFilters != null) {
            mLogFilters!!
                    .filterNot { it.filter(item) }
                    .forEach { return }
        }

        mHandler.sendMessage(Message.obtain(mHandler, MSG_LOG, item))
    }

    override fun handleMessage(msg: Message): Boolean {
        when (msg.what) {
            MSG_LOG -> {
                if (msg.obj !is LogItem) {
                    return true
                }

                val item: LogItem = msg.obj as LogItem

                val content: String = mLogFormatter.format(item)

                when (item.level) {
                    Log.INFO -> {
                        android.util.Log.i(item.tag, content)
                    }
                    Log.ASSERT -> {
                        android.util.Log.e(item.tag, content)
                    }
                    Log.DEBUG -> {
                        android.util.Log.d(item.tag, content)
                    }
                    Log.ERROR -> {
                        android.util.Log.e(item.tag, content)
                    }
                    Log.VERBOSE -> {
                        android.util.Log.v(item.tag, content)
                    }
                    Log.WARN -> {
                        android.util.Log.w(item.tag, content)
                    }
                    Log.WTF -> {
                        android.util.Log.wtf(item.tag, content)
                    }
                }
            }
            else -> {
            }

        }
        return true
    }
}