package com.github.snowdream.util.log.formatter

import com.github.snowdream.util.log.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException

/**
 * Log Formatter
 *
 * Created by snowdream on 17/4/23.
 */
abstract class AbstractLogFormatter {

    /**
     * format the log.
     *
     * @param level
     * *
     * @param tag
     * *
     * @param msg
     * *
     * @return
     */
    abstract fun format(level: Int, tag: String, msg: String, tr: Throwable ?= null): String


    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }

        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }


    /**
     * get level string
     */
    fun getLevelString(level:Int):String{
        if (level< Log.VERBOSE || level > Log.WTF) {
            throw IllegalArgumentException("The log level is invalid")
        }

        when(level){
            Log.INFO -> return "I"
            Log.ASSERT -> return "A"
            Log.DEBUG -> return "D"
            Log.ERROR -> return "E"
            Log.VERBOSE -> return "V"
            Log.WARN -> return "W"
            Log.WTF -> return "WTF"
        }

        return ""
    }
}