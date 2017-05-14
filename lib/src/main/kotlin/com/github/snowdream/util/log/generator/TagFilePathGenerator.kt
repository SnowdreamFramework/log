package com.github.snowdream.util.log.generator

import android.content.Context
import com.github.snowdream.util.log.LogItem
import java.io.File
import java.io.IOException

/**
 * Created by snowdream on 17/4/23.
 */
class TagFilePathGenerator : AbstractFilePathGenerator {

    /**
     * dir will be context.getExternalFilesDir("null").getAbsolutePath() + File.separator + "snowdream" + File.separator + "log"
     * filename will be decided by the param filename and suffix together.
     *
     * @param context
     * @param filename
     * @param suffix
     */
    constructor(context: Context, filename: String, suffix: String) : super(context, filename, suffix)

    /**
     * dir is from the param dir.
     * filename will be decided by the param filename and suffix together.
     *
     * @param dir
     * @param filename
     * @param suffix
     */
    constructor(dir: String, filename: String, suffix: String) : super(dir, filename, suffix)


    override fun generateFilePath(item: LogItem): String {
        val logDir = File(dir)

        if (!logDir.exists()) {
            logDir.mkdirs()
        }

        filename = item.tag

        file = File(logDir, filename + suffix)

        if (!file!!.exists()) {
            try {
                file!!.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return file!!.getAbsolutePath()
    }

    override fun isGenerate(item: LogItem): Boolean {
        val _filename = item.tag

        return (file == null) || !file!!.exists() || !_filename.equals(filename,false)
    }

    override fun onGenerate(newPath: String, oldPath: String) {
    }
}