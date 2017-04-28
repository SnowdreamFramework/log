package com.github.snowdream.util.log

import android.content.Context
import com.github.snowdream.toybricks.ToyBricks

/**
 * Created by snowdream on 17/4/22.
 */
class Log {
    companion object {
        /**
         * Priority constant for the println method; use Log.v.
         */
        val VERBOSE = 2

        /**
         * Priority constant for the println method; use Log.d.
         */
        val DEBUG = 3

        /**
         * Priority constant for the println method; use Log.i.
         */
        val INFO = 4

        /**
         * Priority constant for the println method; use Log.w.
         */
        val WARN = 5

        /**
         * Priority constant for the println method; use Log.e.
         */
        val ERROR = 6

        /**
         * Priority constant for the println method.
         */
        val ASSERT = 7

        /**
         * Priority constant for the println method.
         */
        val WTF = 8

        /**
         * Get logger with custion option
         */
        @JvmStatic fun getLogger(option: LogOption? = null):ILog {
            val logger : ILog = ToyBricks.getImplementation(ILog::class.java)

            if (option != null){
                logger.setOption(option)
            }

            return logger
        }

        /**
         * Get logger to support console or file
         */
        @JvmStatic fun getLogger(context: Context, console: Boolean = true, file: Boolean = false):ILog {
            val logger : ILog = ToyBricks.getImplementation(ILog::class.java)

            logger.setOption(context,console,file)

            return logger
        }
    }
}