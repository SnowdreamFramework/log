package com.github.snowdream.util.log.generator

import android.content.Context
import android.text.TextUtils
import com.github.snowdream.util.log.LogItem
import java.io.File


/**
 * Decide the absolute path of the file which log will be written to.
 *
 * Created by snowdream on 17/4/23.
 */
abstract class AbstractFilePathGenerator {
    private var path: String = ""

    /**
     * if not set, "/mnt/sdcard/snowdream/android/log" will be used.
     */
    protected var dir = "/mnt/sdcard/snowdream/android/log"

    /**
     * if not set, "app" will be used.
     */
    protected var filename = "app"

    /**
     * if not set, ".log" will be used.
     */
    protected var suffix = ".log"

    /**
     * log file
     */
    protected var file: File? = null

    /**
     * dir will be context.getExternalFilesDir("null").getAbsolutePath() + File.separator + "snowdream" + File.separator + "log"
     * filename will be decided by the param filename and suffix together.

     * @param context
     * *
     * @param filename
     * *
     * @param suffix
     */
    constructor(context: Context, filename: String, suffix: String) {

        dir = context.getExternalFilesDir(null).getAbsolutePath() + File.separator + "snowdream" + File.separator + "log"

        if (!TextUtils.isEmpty(filename)) {
            this.filename = filename
        }

        if (!TextUtils.isEmpty(suffix)) {
            this.suffix = suffix
        }
    }

    /**
     * dir is from the param dir.
     * filename will be decided by the param filename and suffix together.
     *
     * @param dir
     * *
     * @param filename
     * *
     * @param suffix
     */
    constructor(dir: String, filename: String, suffix: String) {
        if (!TextUtils.isEmpty(dir)) {
            this.dir = dir
        }

        if (!TextUtils.isEmpty(filename)) {
            this.filename = filename
        }

        if (!TextUtils.isEmpty(suffix)) {
            this.suffix = suffix
        }
    }

    /**
     * Generate the file path of the log.

     * The file path should be absolute.

     * @return the file path of the log
     */
    protected abstract fun generateFilePath(item: LogItem): String

    /**
     * Whetether to generate the file path of the log.

     * @return if true,generate the file path of the log, otherwise not.
     */
    abstract fun isGenerate(item: LogItem): Boolean

    /**
     * It is time to generate the new file path of the log.
     * You can get the new and the old file path of the log.

     * The file path should be absolute.

     * @param newPath the new file path
     * *
     * @param oldPath the old file path
     */
    abstract fun onGenerate(newPath: String, oldPath: String)

    /**
     * Get the file path of the log. generate a new file path  if needed.

     * @return the file path of the log.
     */
    fun getPath(item: LogItem): String {
        val isGenerate: Boolean = isGenerate(item)

        if (TextUtils.isEmpty(path) || isGenerate) {
            val newPath = generateFilePath(item)

            if (!TextUtils.isEmpty(path) && isGenerate) {
                onGenerate(newPath, path)
            }

            path = newPath
        }

        return path
    }
}