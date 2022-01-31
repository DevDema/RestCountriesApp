package com.andreadematteis.assignments.restcountriesapplication.room.model

import androidx.room.*

@Entity(tableName = "country_language_table",
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = arrayOf("country_id"),
            childColumns = arrayOf("country_id")
        ),
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = arrayOf("language_id"),
            childColumns = arrayOf("language_id")
        )
    ],
    indices = [
        Index("country_id"),
        Index("language_id")
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