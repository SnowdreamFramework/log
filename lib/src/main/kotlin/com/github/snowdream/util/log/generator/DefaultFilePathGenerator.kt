package com.github.snowdream.util.log.generator

import android.content.Context
import com.github.snowdream.util.log.generator.AbstractFilePathGenerator
import java.io.File
import java.io.IOException

/**
 * Created by snowdream on 17/4/23.
 */
class DefaultFilePathGenerator : AbstractFilePathGenerator {

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

        file = File(logDir, filename+suffix)

        if (!file!!.exists()) {
            try {
                file!!.createNewFile()
            } catch ( e: IOException) {
                e.printStackTrace()
            }
        }

        return file!!.getAbsolutePath()
    }

    override fun isGenerate():Boolean
    {
        return (file == null) || !file!!.exists()
    }

    override fun onGenerate(newPath: String, oldPath: String) {
    }
}