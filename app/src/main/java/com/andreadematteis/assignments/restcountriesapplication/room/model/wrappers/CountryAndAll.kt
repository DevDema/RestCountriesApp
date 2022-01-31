package com.andreadematteis.assignments.restcountriesapplication.room.model.wrappers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.andreadematteis.assignments.restcountriesapplication.room.model.*

class CountryAndAll(
    @Embedded val countryEntity: CountryEntity,
    @Relation(
        entity = CurrencyEntity::class,
        parentColumn = "country_id",
        entityColumn = "currency_id",
        associateBy = Junction(
            value = CountryCurrencyEntity::class,
            parentColumn = "country_id",
            entityColumn = "currency_id"
        )
    )
    val currenciesEntity: List<CurrencyEntity>,

    @Relation(
        entity = LanguageEntity::class,
        parentColumn = "country_id",
        entityColumn = "language_id",
        associateBy = Junction(
            value = CountryLanguageEntity::class,
            parentColumn = "country_id",
            entityColumn = "language_id"
        )
    )
    val languagesEntity: List<LanguageEntity>,

    @Relation(
        entity = TranslationEntity::class,
        parentColumn = "country_id",
        entityColumn = "translation_id",
        associateBy = Junction(
            value = CountryTranslationEntity::class,
            parentColumn = "country_id",
            entityColumn = "translation_id"
        )
    )
    val translationEntity: List<TranslationEntity>
)