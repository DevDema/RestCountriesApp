package com.andreadematteis.assignments.restcountriesapplication.room.model

import androidx.room.*

@Entity(
    tableName = "country_currency_table",
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = arrayOf("country_id"),
            childColumns = arrayOf("country_id")
        ),
        ForeignKey(
            entity = CurrencyEntity::class,
            parentColumns = arrayOf("currency_id"),
            childColumns = arrayOf("currency_id")
        )
    ],
    indices = [
        Index("country_id", "currency_id")
    ]
)
class CountryCurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "country_currency_id")
    val id: Long,
    @ColumnInfo(name = "country_id")
    val countryId: Long,
    @ColumnInfo(name = "currency_id")
    val currencyId: Long
)