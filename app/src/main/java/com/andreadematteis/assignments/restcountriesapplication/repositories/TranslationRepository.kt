package com.andreadematteis.assignments.restcountriesapplication.repositories

import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.Translation
import com.andreadematteis.assignments.restcountriesapplication.room.CountriesDatabase
import com.andreadematteis.assignments.restcountriesapplication.room.utils.TranslationConverters

class TranslationRepository(
    private val roomDatabase: CountriesDatabase
) {

    suspend fun getAll() = roomDatabase.translationDao().getAll()

    suspend fun insert(translation: Pair<String, Translation>): Long =
        roomDatabase.translationDao().insertTranslation(
            TranslationConverters.toEntity(translation)
        )
}