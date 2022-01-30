package com.andreadematteis.assignments.restcountriesapplication.room.utils


import com.andreadematteis.assignments.restcountriesapplication.room.model.LanguageEntity

object LanguageConverters {

    fun toEntity(currency: Pair<String, String>) = LanguageEntity(
        0,
        currency.first,
        currency.second
    )
}