package com.andreadematteis.assignments.restcountriesapplication.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL


class DownloadImagesForCountryWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        workerParams.inputData.keyValueMap.forEach { (key, value) ->
            val svgString = value as String
            val f = File(context.cacheDir, "$key.png")

            if(f.exists()) {
                return@forEach
            }

            kotlin.runCatching {
                val `in`: InputStream = URL(svgString).openStream()
                val out = FileOutputStream(f)
                val buffer = ByteArray(1024)
                var len: Int

                while (`in`.read(buffer).also { len = it } != -1) {
                    out.write(buffer, 0, len)
                }

            }.exceptionOrNull()?.let {
                Log.w(javaClass.simpleName, it.message ?: "Generic Error")
                it.printStackTrace()

                return Result.failure()

            }
        }

        return Result.success()
    }
}
