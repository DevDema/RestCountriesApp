package com.andreadematteis.assignments.restcountriesapplication.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "country_language_table",
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = arrayOf("country_id"),
            childColumns = arrayOf("country_id")
        ),
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("language_id")
        )
    ])
class CountryLanguageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "country_language_id")
    val id: Long,
    @ColumnInfo(name = "country_id")
    val countryId: Long,
    @ColumnInfo(name = "language_id")
    val languageId: Long
)