package com.andreadematteis.assignments.restcountriesapplication.network

import com.andreadematteis.assignments.restcountriesapplication.network.services.CountriesService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.andreadematteis.assignments.restcountriesapplication.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object CountriesApiModule {

    @Singleton
    @Provides
    fun provideCountriesRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.COUNTRIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun provideCountriesService(retrofit: Retrofit): CountriesService =
        retrofit
            .create(CountriesService::class.java)
}