package com.andreadematteis.assignments.restcountriesapplication.network

import android.content.Context
import androidx.room.Room
import com.andreadematteis.assignments.restcountriesapplication.room.CountriesDatabase
import com.andreadematteis.assignments.restcountriesapplication.room.dao.CountryDao
import com.andreadematteis.assignments.restcountriesapplication.room.dao.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CountriesDatabase {
        return Room.databaseBuilder(
            appContext,
            CountriesDatabase::class.java,
            CountriesDatabase.DATABASE_NAME
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideCountriesDao(
        database: CountriesDatabase
    ): CountryDao =
        database.countriesDao()

    @Singleton
    @Provides
    fun provideCurrencyDao(
        database: CountriesDatabase
    ): CurrencyDao =
        database.currencyDao()
}