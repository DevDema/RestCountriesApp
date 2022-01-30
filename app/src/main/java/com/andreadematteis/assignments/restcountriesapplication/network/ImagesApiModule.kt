package com.andreadematteis.assignments.restcountriesapplication.network

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImagesApiModule {

    @Singleton
    @Provides
    fun provideCoil(@ApplicationContext context: Context): ImageLoader =
        ImageLoader.Builder(context)
            .componentRegistry { add(SvgDecoder(context)) }
            .build()

}