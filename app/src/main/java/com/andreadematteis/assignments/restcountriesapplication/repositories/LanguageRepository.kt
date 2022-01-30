package com.andreadematteis.assignments.restcountriesapplication.repositories

import com.andreadematteis.assignments.restcountriesapplication.room.CountriesDatabase
import com.andreadematteis.assignments.restcountriesapplication.room.utils.LanguageConverters

class LanguageRepository(
    private val roomDatabase: CountriesDatabase
) {

    suspend fun getAll() = roomDatabase.languagesDao().getAll()

    suspend fun insert(languagePair: Pair<String, String>) = roomDatabase.languagesDao().insertLanguage(
        LanguageConverters.toEntity(languagePair)
    )
}