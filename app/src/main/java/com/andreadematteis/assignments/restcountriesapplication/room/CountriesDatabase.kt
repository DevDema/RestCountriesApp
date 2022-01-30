package com.andreadematteis.assignments.restcountriesapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreadematteis.assignments.restcountriesapplication.room.dao.CountryCurrencyDao
import com.andreadematteis.assignments.restcountriesapplication.room.dao.CountryDao
import com.andreadematteis.assignments.restcountriesapplication.room.dao.CurrencyDao
import com.andreadematteis.assignments.restcountriesapplication.room.model.*

@Database(
    entities = [
        CountryEntity::class,
        CurrencyEntity::class,
        LanguageEntity::class,
        TranslationEntity::class,
        CountryTranslationEntity::class,
        CountryCurrencyEntity::class,
        CountryLanguageEntity::class
    ],
    version = 1
)
abstract class CountriesDatabase : RoomDatabase() {

    abstract fun countriesDao(): CountryDao
    abstract fun countryCurrencyDao(): CountryCurrencyDao
    abstract fun currencyDao(): CurrencyDao

    companion object {
        const val DATABASE_NAME = "countries_database"

    }
}