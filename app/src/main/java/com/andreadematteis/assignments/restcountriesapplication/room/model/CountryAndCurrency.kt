package com.andreadematteis.assignments.restcountriesapplication.room.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class CountryAndCurrency(
    @Embedded val countryEntity: CountryEntity,
    @Relation(entity = CurrencyEntity::class, parentColumn = "country_id", entityColumn = "currency_id", associateBy = Junction(
        value = CountryCurrencyEntity::class,
        parentColumn = "country_id",
        entityColumn = "currency_id"
    ))
    val currenciesEntity: List<CurrencyEntity>
)