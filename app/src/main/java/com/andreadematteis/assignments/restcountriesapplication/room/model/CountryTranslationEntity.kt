package com.andreadematteis.assignments.restcountriesapplication.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "country_translation_table",
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = arrayOf("country_id"),
            childColumns = arrayOf("country_id")
        ),
        ForeignKey(
            entity = TranslationEntity::class,
            parentColumns = arrayOf("translation_id"),
            childColumns = arrayOf("translation_id")
        )
    ])
class CountryTranslationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "country_translation_id")
    val id: Long,
    @ColumnInfo(name = "country_id")
    val countryId: Long,
    @ColumnInfo(name = "translation_id")
    val translationId: Long
)