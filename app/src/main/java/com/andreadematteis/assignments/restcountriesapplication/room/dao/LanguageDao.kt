package com.andreadematteis.assignments.restcountriesapplication.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreadematteis.assignments.restcountriesapplication.room.model.LanguageEntity

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLanguage(language: LanguageEntity): Long

    @Query("SELECT * FROM languages_table WHERE languages_table.language_id == :id")
    suspend fun getLanguage(id: Int): LanguageEntity

    @Query("SELECT * FROM languages_table")
    suspend fun getAll(): List<LanguageEntity>
}