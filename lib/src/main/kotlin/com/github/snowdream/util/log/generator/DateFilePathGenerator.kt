package com.github.snowdream.util.log.generator

import android.content.Context
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by snowdream on 17/4/23.
 */
class DateFilePathGenerator : AbstractFilePathGenerator {

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


    override fun generateFilePath(): String {
        val logDir = File(dir)
        if (!logDir.exists()) {
            logDir.mkdirs()
        }

        val myDate = Date()

        val format = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
        val myDateString = format.format(myDate)

        val buffer = StringBuffer()
        buffer.append(filename)
        buffer.append("-")
        buffer.append(myDateString)
        buffer.append(suffix)

        file = File(logDir, buffer.toString())

        if (!file!!.exists()) {
            try {
                file!!.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return file!!.absolutePath
    }

    override fun isGenerate(): Boolean {
        return (file == null) || !file!!.exists()
    }

    override fun onGenerate(newPath: String, oldPath: String) {
    }
}