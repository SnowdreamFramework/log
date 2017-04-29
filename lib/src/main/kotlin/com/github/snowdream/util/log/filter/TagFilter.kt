package com.github.snowdream.util.log.filter

import android.text.TextUtils
import com.github.snowdream.util.log.LogItem
import java.util.regex.Pattern

/**
 * Created by snowdream on 17/4/24.
 */
class TagFilter : AbstractLogFilter {
    private var tagList: MutableList<String> = mutableListOf()

    private var tagRegularMap: MutableMap<String, Pattern> = mutableMapOf()

    /**
     * whether the tag is Regular Expression (RegExp)
     */
    private var isRegular = false

    constructor(tag: String, isRegular: Boolean = false) {
        if (!tagList.contains(tag)) {
            tagList.add(tag)
        }

        this.isRegular = isRegular

        if (isRegular) {
            if (!tagRegularMap.containsKey(tag)) {
                val pattern = Pattern.compile(tag)
                tagRegularMap.put(tag, pattern)
            }
        }
    }


    constructor(tags: List<String>, isRegular: Boolean = false) {
        for (tag in tags) {
            if (TextUtils.isEmpty(tag)) {
                continue
            }

            if (!tagList.contains(tag)) {
                tagList.add(tag)
            }

            this.isRegular = isRegular

            if (isRegular) {
                if (!tagRegularMap.containsKey(tag)) {
                    val pattern = Pattern.compile(tag)
                    tagRegularMap.put(tag, pattern)
                }
            }
        }
    }

    override fun filter(item: LogItem): Boolean {
        if (tagList.isEmpty() && tagRegularMap.isEmpty()) {
            return true
        }

        if (isRegular) {
            for ((regular, pattern) in tagRegularMap) {
                val matcher = pattern.matcher(item.msg)

                if (matcher.matches()) {
                    return true
                }
            }
        } else {
            if (tagList.contains(item.tag)) {
                return true
            }
        }

        return false
    }
}