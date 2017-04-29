package com.github.snowdream.util.log.processor

import java.io.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * Log File Manager
 *
 * Created by snowdream on 17/4/27.
 */
class LogFileManager {
    private var mReadWriteLock: ReentrantReadWriteLock = ReentrantReadWriteLock()

    private var mServiceScheduled: ScheduledExecutorService = Executors
            .newSingleThreadScheduledExecutor()

    init {
        mServiceScheduled.scheduleAtFixedRate(ScheduledClearTask(), 10, 10, TimeUnit.SECONDS);
    }

    /**
     * log file
     */
    private var logFileMap: MutableMap<String, LogFile> = mutableMapOf()

    fun write(filePath: String, log: String) {
        mReadWriteLock.readLock().lock()

        var hasCache: Boolean = false

        try {
            val logfile: LogFile? = logFileMap.get(filePath)

            hasCache = logfile != null

            if (logfile != null) {
                logfile.writer.println(log)
            }

        } catch(e: IOException) {
            e.printStackTrace()
        } finally {
            mReadWriteLock.readLock().unlock()
        }

        if (!hasCache) {
            mReadWriteLock.writeLock().lock()

            try {
                val logfile: LogFile = LogFile(
                        File(filePath),
                        filePath,
                        PrintWriter(BufferedWriter(FileWriter(filePath, true)))
                )

                logfile.writer.println(log)

                logFileMap.put(filePath, logfile)
            } catch(e: IOException) {
                e.printStackTrace()
            } finally {
                mReadWriteLock.writeLock().unlock()
            }
        }
    }

    private inner class ScheduledClearTask : Runnable {
        val TIME_OUT: Long = 30 * 1000

        override fun run() {
            mReadWriteLock.writeLock().lock()

            try {
                val it = logFileMap.iterator()
                while (it.hasNext()) {
                    val entry = it.next()

                    val logfile: LogFile = entry.value

                    val lastModifiedTime: Long = logfile.file.lastModified()

                    val time = System.currentTimeMillis() - lastModifiedTime

                    if (time >= TIME_OUT) {
                        logfile.writer.flush()
                        logfile.writer.close()

                        it.remove()
                    }else{
                        logfile.writer.flush()
                    }
                }
            } catch(e: IOException) {
                e.printStackTrace()
            } finally {
                mReadWriteLock.writeLock().unlock()
            }
        }
    }

    fun close() {
        mServiceScheduled.shutdown()
    }

    private data class LogFile(val file: File, val filePath: String, var writer: PrintWriter)
}