package com.github.snowdream.util.log.processor

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import com.github.snowdream.util.log.LogItem
import com.github.snowdream.util.log.filter.AbstractLogFilter
import com.github.snowdream.util.log.formatter.AbstractLogFormatter
import com.github.snowdream.util.log.formatter.DefaultFormatter
import com.github.snowdream.util.log.generator.AbstractFilePathGenerator
import com.github.snowdream.util.log.generator.DefaultFilePathGenerator

/**
 * Log File Processor
 *
 * Created by snowdream on 17/4/24.
 */
class LogFileProcessor : AbstractLogProcessor {
    private var mLogFileManager: LogFileManager = LogFileManager()

    constructor(context: Context) : super(context) {
        mLogFormatter = DefaultFormatter()
        mFilePathGenerator = DefaultFilePathGenerator(context, "app", ".log")

        mHandlerThread = HandlerThread("LogFileProcessor")
        mHandlerThread.start()

        mHandler = Handler(mHandlerThread.looper, this)
    }

    constructor(context: Context, logFormatter: AbstractLogFormatter, filePathGenerator: AbstractFilePathGenerator, logFilters: List<AbstractLogFilter>? = null) : super(context, logFormatter, filePathGenerator, logFilters) {
        mHandlerThread = HandlerThread("LogFileProcessor")
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
                val filePath: String = mFilePathGenerator.getPath()

                mLogFileManager.write(filePath, content)
            }
            else -> {
            }

        }
        return true
    }
}