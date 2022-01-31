package com.andreadematteis.assignments.restcountriesapplication.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryCurrencyEntity

@Dao
interface CountryCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCountryCurrency(country: CountryCurrencyEntity): Long
}