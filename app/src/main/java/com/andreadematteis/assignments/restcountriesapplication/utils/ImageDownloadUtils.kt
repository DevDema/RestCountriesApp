package com.andreadematteis.assignments.restcountriesapplication.utils

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL

object ImageDownloadUtils {

    fun downloadPng(url: String, targetDir: File, fileName: String) {
        val file = File(targetDir, "$fileName.png")
        val `in`: InputStream = URL(url).openStream()
        val out = FileOutputStream(file)
        val buffer = ByteArray(1024)
        var len: Int

        while (`in`.read(buffer).also { len = it } != -1) {
            out.write(buffer, 0, len)
        }

        `in`.close()
        out.close()
    }
}