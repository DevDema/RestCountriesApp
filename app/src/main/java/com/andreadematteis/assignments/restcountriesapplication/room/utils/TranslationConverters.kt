package com.andreadematteis.assignments.restcountriesapplication.room.utils

import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.Translation
import com.andreadematteis.assignments.restcountriesapplication.room.model.TranslationEntity

object TranslationConverters {

    fun toEntity(currency: Pair<String, Translation>) = TranslationEntity(
        0,
        currency.first,
        currency.second.common,
        currency.second.official
    )
}