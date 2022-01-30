package com.andreadematteis.assignments.restcountriesapplication.network

import coil.ImageLoader
import com.andreadematteis.assignments.restcountriesapplication.network.repositories.CountryRepository
import com.andreadematteis.assignments.restcountriesapplication.network.repositories.ImagesRepository
import com.andreadematteis.assignments.restcountriesapplication.network.services.CountriesService
import com.andreadematteis.assignments.restcountriesapplication.room.CountriesDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCountriesRepository(
        countriesService: CountriesService,
        roomDatabase: CountriesDatabase
    ): CountryRepository =
        CountryRepository(countriesService, roomDatabase)

    @Singleton
    @Provides
    fun provideImagesRepository(
        imageLoader: ImageLoader
    ): ImagesRepository =
        ImagesRepository(imageLoader)
}