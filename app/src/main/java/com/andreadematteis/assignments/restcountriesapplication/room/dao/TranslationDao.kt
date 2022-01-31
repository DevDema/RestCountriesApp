package com.andreadematteis.assignments.restcountriesapplication.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreadematteis.assignments.restcountriesapplication.room.model.TranslationEntity

@Dao
interface TranslationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTranslation(language: TranslationEntity): Long

    @Query("SELECT * FROM translations_table WHERE translations_table.translation_id == :id")
    suspend fun getTranslation(id: Int): TranslationEntity

    @Query("SELECT * FROM translations_table")
    suspend fun getAll(): List<TranslationEntity>
}