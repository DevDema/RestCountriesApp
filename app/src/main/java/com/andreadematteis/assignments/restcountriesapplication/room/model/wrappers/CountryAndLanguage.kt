package com.andreadematteis.assignments.restcountriesapplication.room.model.wrappers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryLanguageEntity
import com.andreadematteis.assignments.restcountriesapplication.room.model.LanguageEntity

class CountryAndLanguage(
    @Embedded val countryEntity: CountryEntity,
    @Relation(entity = LanguageEntity::class, parentColumn = "country_id", entityColumn = "language_id", associateBy = Junction(
        value = CountryLanguageEntity::class,
        parentColumn = "country_id",
        entityColumn = "language_id"
    ))
    val languagesEntity: List<LanguageEntity>
)