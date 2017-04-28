package com.github.snowdream.util.log.processor

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import com.github.snowdream.util.log.LogItem
import com.github.snowdream.util.log.formatter.AbstractLogFormatter
import com.github.snowdream.util.log.generator.AbstractFilePathGenerator

/**
 * Created by snowdream on 17/4/23.
 */
abstract class AbstractLogProcessor :Handler.Callback{
    /**
     * Log Formatter
     */
    protected lateinit var mLogFormatter: AbstractLogFormatter

    /**
     * Log File Path Generator
     */
    protected lateinit var mFilePathGenerator: AbstractFilePathGenerator

    /**
     *  Handler Thread
    */
    protected lateinit var mHandlerThread:HandlerThread

    /**
     *  Handler
     */
    protected lateinit var mHandler: Handler

    /**
     * MSG
     */
    protected val MSG_LOG:Int = 0x10

    constructor(context: Context)

    constructor(context: Context,logFormatter: AbstractLogFormatter, filePathGenerator: AbstractFilePathGenerator) {
        mLogFormatter = logFormatter
        mFilePathGenerator = filePathGenerator
    }

    /**
     * process the log.
     *
     * @param level
     * *
     * @param tag
     * *
     * @param msg
     * *
     * @return
     */
    abstract fun process(item:LogItem)
}