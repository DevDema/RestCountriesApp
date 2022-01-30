package com.andreadematteis.assignments.restcountriesapplication.network.repositories

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import coil.ImageLoader
import coil.request.ImageRequest
import coil.target.Target


class ImagesRepository(private val imageLoader: ImageLoader) {

    fun downloadSvg(context: Context, title: String, description: String, uri: String) {
        imageLoader.enqueue(
            ImageRequest.Builder(context)
                .crossfade(true)
                .crossfade(500)
                .data(uri)
                .target(object : Target {

                    override fun onSuccess(result: Drawable) {
                        super.onSuccess(result)

                        val bitmap = (result as BitmapDrawable).bitmap
                        MediaStore.Images.Media.insertImage(
                            context.contentResolver,
                            bitmap,
                            title,
                            description
                        );
                    }
                }).build()
        )
    }
}