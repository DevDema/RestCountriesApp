package com.andreadematteis.assignments.restcountriesapplication.room.model.wrappers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.andreadematteis.assignments.restcountriesapplication.room.model.*

class CountryAndTranslation(
    @Embedded val countryEntity: CountryEntity,
    @Relation(entity = TranslationEntity::class, parentColumn = "country_id", entityColumn = "translation_id", associateBy = Junction(
        value = CountryTranslationEntity::class,
        parentColumn = "country_id",
        entityColumn = "translation_id"
    ))
    val translationEntity: List<TranslationEntity>
)