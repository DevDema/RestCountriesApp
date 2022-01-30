package com.andreadematteis.assignments.restcountriesapplication.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreadematteis.assignments.restcountriesapplication.room.model.CurrencyEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCurrency(country: CurrencyEntity): Long

    @Query("SELECT * FROM currencies_table WHERE currencies_table.currency_id == :id")
    fun getCurrency(id: Int): CurrencyEntity

    @Query("SELECT * FROM currencies_table")
    fun getAll(): List<CurrencyEntity>
}