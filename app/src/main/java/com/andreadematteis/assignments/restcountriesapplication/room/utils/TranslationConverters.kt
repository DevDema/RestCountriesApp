package com.andreadematteis.assignments.restcountriesapplication.room.utils

import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.Translation
import com.andreadematteis.assignments.restcountriesapplication.room.model.TranslationEntity

object TranslationConverters {

    fun toEntity(translation: Pair<String?, Translation?>?) = TranslationEntity(
        0,
        translation?.first ?: "",
        translation?.second?.common ?: "",
        translation?.second?.official ?: ""
    )
}