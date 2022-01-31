package com.andreadematteis.assignments.restcountriesapplication.room.dao

import androidx.room.*
import com.andreadematteis.assignments.restcountriesapplication.room.model.wrappers.CountryAndCurrency
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity
import com.andreadematteis.assignments.restcountriesapplication.room.model.wrappers.CountryAndAll
import com.andreadematteis.assignments.restcountriesapplication.room.model.wrappers.CountryAndLanguage
import com.andreadematteis.assignments.restcountriesapplication.room.model.wrappers.CountryAndTranslation

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: CountryEntity): Long

    @Query("SELECT * FROM countries_table INNER JOIN country_currency_table ON countries_table.country_id = country_currency_table.country_id")
    suspend fun getCompleteCountries(): List<CountryAndAll>

    @Query("SELECT * FROM countries_table INNER JOIN country_currency_table ON countries_table.country_id = country_currency_table.country_id")
    suspend fun getCountriesWithCurrency(): List<CountryAndCurrency>

    @Query("SELECT * FROM countries_table INNER JOIN country_language_table ON countries_table.country_id = country_language_table.country_id")
    suspend fun getCountriesWithLanguages(): List<CountryAndLanguage>

    @Query("SELECT * FROM countries_table INNER JOIN country_translation_table ON countries_table.country_id = country_translation_table.country_id")
    suspend fun getCountriesWithTranslation(): List<CountryAndTranslation>

    @Query("SELECT * FROM countries_table")
    suspend fun getCountries(): List<CountryEntity>
}