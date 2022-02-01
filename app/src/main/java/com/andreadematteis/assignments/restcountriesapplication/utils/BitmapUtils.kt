package com.andreadematteis.assignments.restcountriesapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object BitmapUtils {

    fun bitmapFromAssets(context: Context, file: String): Bitmap {
        val inputStream = context.assets.open(file)

        return BitmapFactory.decodeStream(inputStream)
    }
}