package com.andreadematteis.assignments.restcountriesapplication.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryLanguageEntity

@Dao
interface CountryLanguageDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCountryLanguage(countryLanguageEntity: CountryLanguageEntity): Long
}