package com.andreadematteis.assignments.restcountriesapplication.room.utils


import com.andreadematteis.assignments.restcountriesapplication.room.model.LanguageEntity

object LanguageConverters {

    fun toEntity(languagePair: Pair<String, String>) = LanguageEntity(
        0,
        languagePair.first,
        languagePair.second
    )
}