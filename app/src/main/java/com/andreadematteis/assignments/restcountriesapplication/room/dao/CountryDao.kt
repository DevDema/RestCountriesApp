package com.andreadematteis.assignments.restcountriesapplication.room.dao

import androidx.room.*
import com.andreadematteis.assignments.restcountriesapplication.model.Country
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryAndCurrency
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCountry(country: CountryEntity): Long

    @Query("SELECT * FROM countries_table INNER JOIN country_currency_table ON countries_table.country_id = country_currency_table.country_id")
    fun getAll(): List<CountryAndCurrency>

    @Query("SELECT * FROM countries_table")
    fun getCountries(): List<CountryEntity>
}