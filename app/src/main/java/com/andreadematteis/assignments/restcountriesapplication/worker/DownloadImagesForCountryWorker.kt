package com.andreadematteis.assignments.restcountriesapplication.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.andreadematteis.assignments.restcountriesapplication.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import kotlin.math.roundToInt


class DownloadImagesForCountryWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val toBeDownloaded = workerParams.inputData.keyValueMap
            .map { (key, value) ->
                value as String to File(context.cacheDir, "$key.png")
            }
            .filterNot { it.second.exists() }

        if (toBeDownloaded.isEmpty()) {
            return Result.success()
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        var currentProgress = 0F
        val notificationId = 5069
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setOngoing(true)
            .setProgress(NOTIFICATION_PROGRESS_MAX, currentProgress.toInt(), false)
            .setContentTitle(context.getString(R.string.downloading_country_flag_label))
            .setContentText(context.getString(R.string.downloading_country_flag_message))
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    android.R.drawable.stat_sys_download
                )
            )
            .setSmallIcon(
                android.R.drawable.stat_sys_download
            )

        notificationManager.notify(notificationId, notificationBuilder.build())

        val incrementProgress = NOTIFICATION_PROGRESS_MAX.toFloat() / toBeDownloaded.size.toFloat()
        toBeDownloaded.forEach {
            kotlin.runCatching {
                val `in`: InputStream = URL(it.first).openStream()
                val out = FileOutputStream(it.second)
                val buffer = ByteArray(1024)
                var len: Int

                while (`in`.read(buffer).also { len = it } != -1) {
                    out.write(buffer, 0, len)
                }

                currentProgress += incrementProgress
                notificationManager.notify(
                    notificationId, notificationBuilder
                        .setProgress(NOTIFICATION_PROGRESS_MAX, currentProgress.roundToInt(), false)
                        .build()
                )
            }.exceptionOrNull()?.let {
                Log.w(javaClass.simpleName, it.message ?: "Generic Error")
                it.printStackTrace()

                notificationManager.cancel(notificationId)

                val errorNotificationId = 9091
                val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(context.getString(R.string.download_country_flag_failed))
                    .setContentText(context.getString(R.string.download_country_flag_failed_message))
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            context.resources,
                            android.R.drawable.stat_sys_download_done
                        )
                    )
                    .setSmallIcon(
                        android.R.drawable.stat_sys_download_done
                    )
                    .build()

                notificationManager.notify(errorNotificationId, notification)
                return Result.failure()

            }
        }

        notificationManager.cancel(notificationId)

        return Result.success()

    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "app_activity"
        private const val NOTIFICATION_PROGRESS_MAX = 100
    }
}
