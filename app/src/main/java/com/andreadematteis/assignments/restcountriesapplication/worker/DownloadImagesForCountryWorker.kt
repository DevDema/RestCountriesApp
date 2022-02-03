package com.andreadematteis.assignments.restcountriesapplication.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.webkit.URLUtil
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.utils.ImageDownloadUtils

import java.io.File
import kotlin.math.roundToInt

class DownloadImagesForCountryWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val baseUrl = workerParams.inputData.keyValueMap[BASE_URL_KEY]
        val toBeDownloaded = workerParams.inputData.keyValueMap
            .filterNot { it.key == BASE_URL_KEY }
            .map { (key, value) ->
                key to value as String
            }
            .sortedBy { it.second }
            .filterNot { File(context.cacheDir, "${it.first}.png").exists() }

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
            .setContentTitle(
                if (baseUrl == COATS_BASE_URL) {
                    context.getString(R.string.downloading_coats_of_arms_label)
                } else {
                    context.getString(R.string.downloading_country_flag_label)
                }
            )
            .setContentText(
                if (baseUrl == COATS_BASE_URL) {
                    context.getString(R.string.downloading_coats_of_arms_message)
                } else {
                    context.getString(R.string.downloading_country_flag_message)
                }
            )
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
                if (URLUtil.isValidUrl(it.second)) {
                    ImageDownloadUtils.downloadPng(
                        it.second,
                        context.cacheDir,
                        it.first
                    )
                } else {
                    ImageDownloadUtils.downloadPng(
                        "${baseUrl}${it.second}",
                        context.cacheDir,
                        it.first
                    )
                }

                currentProgress += incrementProgress
                notificationManager.notify(
                    notificationId, notificationBuilder
                        .setProgress(
                            NOTIFICATION_PROGRESS_MAX,
                            currentProgress.roundToInt(),
                            false
                        )
                        .build()
                )
            }.exceptionOrNull()?.let {
                Log.w(javaClass.simpleName, it.message ?: "Generic Error")
                it.printStackTrace()

                notificationManager.cancel(notificationId)

                val errorNotificationId = 9091
                val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(context.getString(R.string.download_failed))
                    .setContentText(if (baseUrl == COATS_BASE_URL) {
                        context.getString(R.string.download_coats_of_arms_failed_message)
                    } else {
                        context.getString(R.string.download_country_flag_failed_message)
                    })
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

        const val FLAGS_BASE_URL = "https://flagcdn.com/w320/"
        const val COATS_BASE_URL = "https://mainfacts.com/media/images/coats_of_arms/"
        const val BASE_URL_KEY = "base_url"

    }
}
