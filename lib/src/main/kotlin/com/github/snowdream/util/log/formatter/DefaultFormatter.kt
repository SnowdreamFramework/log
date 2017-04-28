package com.github.snowdream.util.log.formatter

/**
 * Default log formatter for console and file
 *
 * Created by snowdream on 17/4/24.
 */
class DefaultFormatter : AbstractLogFormatter() {

    override fun format(level: Int, tag: String, msg: String, tr: Throwable?): String {
        if(tr == null){
            return msg
        }else{
            return msg + '\n' + getStackTraceString(tr)
        }
    }
}