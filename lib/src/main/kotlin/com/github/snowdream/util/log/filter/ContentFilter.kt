package com.github.snowdream.util.log.filter

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * Created by snowdream on 17/4/24.
 */
class ContentFilter : AbstractLogFilter{
    private var msgList: MutableList<String> = mutableListOf()

    private var msgRegularMap: MutableMap<String, Pattern> = mutableMapOf()

    /**
     * whether the msg is Regular Expression (RegExp)
     */
    private var isRegular = false

    constructor(msg: String, isRegular: Boolean = false) {
        if (!msgList.contains(msg)){
            msgList.add(msg)
        }

        this.isRegular = isRegular

        if (isRegular){
            if (!msgRegularMap.containsKey(msg)){
                val pattern  = Pattern.compile(msg)
                msgRegularMap.put(msg,pattern)
            }
        }
    }


    constructor(msgs: List<String>,isRegular: Boolean = false){
        for (msg in msgs){
            if (TextUtils.isEmpty(msg)){
                continue
            }

            if (!msgList.contains(msg)){
                msgList.add(msg)
            }

            this.isRegular = isRegular

            if (isRegular){
                if (!msgRegularMap.containsKey(msg)){
                    val pattern  = Pattern.compile(msg)
                    msgRegularMap.put(msg,pattern)
                }
            }
        }
    }

    override fun filter(level: Int, tag: String, msg: String): Boolean {
        if (msgList.isEmpty() && msgRegularMap.isEmpty()){
            return true
        }

        if (isRegular){
            for ((regular, pattern) in msgRegularMap) {
                val matcher  = pattern.matcher(msg)

                if (matcher.matches()){
                    return true
                }
            }
        }else{
            msgList
                    .filter { msg.contains(it) }
                    .forEach { return true }
        }

        return false
    }
}